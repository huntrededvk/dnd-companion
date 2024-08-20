package com.khve.feature_meta.data.network.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.khve.feature_auth.data.network.firebase.FirebaseUserManager
import com.khve.feature_auth.domain.entity.Permission
import com.khve.feature_auth.domain.entity.UserRole
import com.khve.feature_auth.domain.entity.UserState
import com.khve.feature_meta.data.mapper.MetaMapper
import com.khve.feature_meta.data.model.MetaCardItemDto
import com.khve.feature_meta.data.model.MetaItemDto
import com.khve.feature_meta.domain.entity.BuildKarma
import com.khve.feature_meta.domain.entity.MetaCardItem
import com.khve.feature_meta.domain.entity.MetaCardListState
import com.khve.feature_meta.domain.entity.MetaItem
import com.khve.feature_meta.domain.entity.MetaItemState
import com.khve.feature_meta.domain.entity.PartySizeEnum
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseMetaManager @Inject constructor(
    private val userManager: FirebaseUserManager,
    private val metaMapper: MetaMapper
) {

    private val mDocRef = FirebaseFirestore.getInstance()
        .collection("meta")

    private val _metaCardListState = MutableStateFlow<MetaCardListState>(
        MetaCardListState.Initial
    )
    private val metaCardListState = _metaCardListState.asStateFlow()

    private val _metaItemState = MutableStateFlow<MetaItemState>(
        MetaItemState.Initial
    )
    private val metaItemState = _metaItemState.asStateFlow()

    fun like(metaItem: MetaItem) {
        toggleLikeDislike(metaItem, isLike = true)
    }

    fun dislike(metaItem: MetaItem) {
        toggleLikeDislike(metaItem, isLike = false)
    }

    private fun toggleLikeDislike(metaItem: MetaItem, isLike: Boolean) {
        val userId = userManager.getCurrentUserId()
        val partySize = metaItem.partySize
        val hasPermission = hasPermission(Permission.LIKE_DISLIKE_BUILD)

        if (userId == null || partySize == null || !hasPermission) {
            _metaItemState.value = MetaItemState.Error("Forbidden")
            _metaItemState.value = MetaItemState.Initial
            return
        }

        val buildKarma = BuildKarma(userId)
        val metaItemToSave = metaItem.copy(
            likes = if (isLike) {
                toggleItem(metaItem.likes, buildKarma)
            } else {
                metaItem.likes.filterNot { it.userId == userId }
            },
            dislikes = if (isLike) {
                metaItem.dislikes.filterNot { it.userId == userId }
            } else {
                toggleItem(metaItem.dislikes, buildKarma)
            }
        )

        mDocRef.document(META_BUILDS).collection(partySize.name).document(metaItemToSave.uid)
            .set(metaItemToSave)
            .addOnSuccessListener {
                _metaItemState.value = MetaItemState.MetaItem(metaItemToSave)
                updateCardItemInMetaCardList(metaItemToSave)
            }
    }

    private fun updateCardItemInMetaCardList(metaItem: MetaItem) {
        val metaCardListState = _metaCardListState.value
        if (metaCardListState is MetaCardListState.MetaCardList) {
            _metaCardListState.value = MetaCardListState.MetaCardList(
                metaCardListState.metaCardList.map { metaCard ->
                    if (metaCard.uid == metaItem.uid) {
                        metaCard.copy(likes = metaItem.likes, dislikes = metaItem.dislikes)
                    } else {
                        metaCard
                    }
                }
            )
        }
    }

    private fun toggleItem(items: List<BuildKarma>, buildKarma: BuildKarma): List<BuildKarma> {
        return if (items.any { it.userId == buildKarma.userId }) {
            items.filterNot { it.userId == buildKarma.userId }
        } else {
            items + buildKarma
        }
    }

    fun getMetaCardList(partySize: PartySizeEnum): StateFlow<MetaCardListState> {
        _metaCardListState.value = MetaCardListState.Initial

        mDocRef.document(META_BUILDS).collection(partySize.name).get()
            .addOnSuccessListener { snapshot ->
                val metaList = mutableListOf<MetaCardItem>()
                val snapshotList = snapshot?.documents ?: emptyList()

                for (currentSnapshot in snapshotList) {
                    val retrievedMetaItem =
                        currentSnapshot?.toObject(MetaCardItemDto::class.java)
                    retrievedMetaItem?.let {
                        val metaCardItem = metaMapper.metaCardItemDtoToMetaCardItem(
                            it.copy(uid = currentSnapshot.id)
                        )
                        if (it.activated || isAuthor(metaCardItem.author[MetaItemDto.USER_UID])
                            || isAdminOrModerator()
                        ) {
                            metaList.add(metaCardItem)
                        }
                    }
                }

                if (metaList.isEmpty()) {
                    _metaCardListState.value =
                        MetaCardListState.Error("We don't have any meta yet")
                }

                _metaCardListState.value = MetaCardListState.MetaCardList(metaList)
            }
            .addOnFailureListener {
                _metaCardListState.value =
                    MetaCardListState.Error(it.localizedMessage ?: "Unknown error")
            }

        return metaCardListState
    }

    private fun isAuthor(metaItemUid: String?): Boolean {
        val currentUser = userManager.getCurrentDbUserState() as? UserState.User
        return currentUser?.user?.uid == metaItemUid
    }

    private fun isAdminOrModerator(): Boolean {
        val currentUser = userManager.getCurrentDbUserState() as? UserState.User
        return currentUser?.user?.hasOneOfRoles(
            currentUser.user.role,
            listOf(UserRole.ADMIN, UserRole.MODERATOR)
        ) == true
    }

    private fun isAuthorOrHasPermission(
        metaItemDto: MetaItemDto,
        permission: Permission
    ): Boolean {
        val currentUser = userManager.getCurrentDbUserState() as? UserState.User
        val isCurrentUserAuthor =
            currentUser?.user?.uid == metaItemDto.author[MetaItemDto.USER_UID]
        val hasRole = currentUser?.user?.hasPermission(permission) ?: false
        return isCurrentUserAuthor || hasRole
    }

    private fun hasPermission(permission: Permission): Boolean {
        return (userManager.getCurrentDbUserState() as? UserState.User)?.user?.hasPermission(
            permission
        ) ?: false
    }

    fun addMetaItem(metaItem: MetaItem): StateFlow<MetaItemState> {
        if (hasPermission(Permission.ADD_META_ITEM)) {
            var mappedMetaItemDto = metaMapper.metaItemToMetaItemDto(metaItem)

            if (hasPermission(Permission.APPROVE_SELF_META_ITEM)) {
                mappedMetaItemDto = mappedMetaItemDto.copy(activated = true)
            }

            if (!isValidated(
                    metaItemDto = mappedMetaItemDto,
                    titleMinLength = 3,
                    titleMaxLength = 50,
                    descriptionMinLength = 10,
                    descriptionMaxLength = 10000
                )
            )
                return metaItemState

            mDocRef.document(META_BUILDS).collection(
                metaItem.partySize?.name
                    ?: throw IllegalArgumentException("Can not add meta item, party size is null")
            ).document()
                .set(mappedMetaItemDto)
                .addOnSuccessListener {
                    resetStateAndUpdateMetaList(mappedMetaItemDto.partySize)
                    _metaItemState.value = MetaItemState.Success
                }
                .addOnFailureListener { e ->
                    _metaItemState.value = MetaItemState.Error(
                        e.localizedMessage ?: "Unknown error"
                    )
                }
        } else {
            _metaItemState.value = MetaItemState.Error("Forbidden")
        }

        return metaItemState
    }

    private fun resetStateAndUpdateMetaList(partySize: PartySizeEnum?) {
        _metaItemState.value = MetaItemState.Initial
        getMetaCardList(
            partySize
                ?: throw IllegalArgumentException("Can not update MetaCardList after Meta Item was deleted, party size is null")
        )
    }

    fun deleteMetaItem(metaItem: MetaItem): StateFlow<MetaItemState> {
        val mappedMetaItemDto = metaMapper.metaItemToMetaItemDto(metaItem)

        if (isAuthorOrHasPermission(mappedMetaItemDto, Permission.DELETE_META_ITEM)) {
            mDocRef.document(META_BUILDS).collection(
                metaItem.partySize?.name
                    ?: throw IllegalArgumentException("Can not delete meta item, party size is null")
            )
                .document(metaItem.uid).delete()
                .addOnSuccessListener {
                    resetStateAndUpdateMetaList(mappedMetaItemDto.partySize)
                    _metaItemState.value = MetaItemState.Success
                }
                .addOnFailureListener {
                    _metaItemState.value = MetaItemState.Error(
                        it.localizedMessage ?: "Couldn't delete meta data"
                    )
                }
        }

        return metaItemState
    }

    fun activateMetaItem(metaItem: MetaItem): StateFlow<MetaItemState> {
        if (isAdminOrModerator()) {
            val mappedMetaItemDto = metaMapper.metaItemToMetaItemDto(metaItem)
            val metaItemDtoApproved = mappedMetaItemDto.copy(activated = true)
            mDocRef.document(META_BUILDS).collection(
                metaItem.partySize?.name
                    ?: throw IllegalArgumentException("Can not approve meta item, party size is null")
            )
                .document(metaItemDtoApproved.uid).set(metaItemDtoApproved)
                .addOnSuccessListener {
                    resetStateAndUpdateMetaList(metaItemDtoApproved.partySize)
                    _metaItemState.value = MetaItemState.Success
                }
                .addOnFailureListener {
                    _metaItemState.value = MetaItemState.Error(
                        it.localizedMessage ?: "Couldn't approve meta data"
                    )
                }
        }

        return metaItemState
    }

    fun updateMetaItem(metaItem: MetaItem): StateFlow<MetaItemState> {
        val mappedMetaItemDto = metaMapper.metaItemToMetaItemDto(metaItem)

        if (isAuthorOrHasPermission(mappedMetaItemDto, Permission.EDIT_META_ITEM)) {
            mDocRef.document(META_BUILDS).collection(
                metaItem.partySize?.name
                    ?: throw IllegalArgumentException("Can not update meta item, party size is null")
            )
                .document(mappedMetaItemDto.uid).set(mappedMetaItemDto)
                .addOnSuccessListener {
                    resetStateAndUpdateMetaList(mappedMetaItemDto.partySize)
                    _metaItemState.value = MetaItemState.Success
                }
                .addOnFailureListener {
                    _metaItemState.value = MetaItemState.Error(
                        it.localizedMessage ?: "Couldn't delete meta data"
                    )
                }
        }

        return metaItemState
    }

    fun getMetaItem(
        metaItemUid: String,
        partySize: PartySizeEnum
    ): StateFlow<MetaItemState> {
        _metaItemState.value = MetaItemState.Initial
        mDocRef.document(META_BUILDS).collection(partySize.name).document(metaItemUid).get()
            .addOnSuccessListener { snapshot ->
                val metaItemDto = snapshot?.toObject(MetaItemDto::class.java)
                _metaItemState.value = if (metaItemDto != null) {
                    val metaItemDtoWithUid = metaItemDto.copy(uid = snapshot.id)
                    val mappedMetaItem = metaMapper.metaItemDtoToMetaItem(metaItemDtoWithUid)
                    MetaItemState.MetaItem(
                        mappedMetaItem
                    )
                } else {
                    MetaItemState.Error("Meta data is empty")
                }
            }
            .addOnFailureListener {
                _metaItemState.value = MetaItemState.Error(
                    it.localizedMessage ?: "Unknown error"
                )
            }

        return metaItemState
    }

    private fun isValidated(
        metaItemDto: MetaItemDto,
        titleMinLength: Int,
        titleMaxLength: Int,
        descriptionMinLength: Int,
        descriptionMaxLength: Int
    ): Boolean {
        var error = ""

        if (metaItemDto.partySize == null)
            throw IllegalArgumentException("Party size can not be empty")

        if (metaItemDto.youtubeVideoId.isNotEmpty() && metaItemDto.youtubeVideoId.length != 11) {
            error = "Youtube video id is incorrect"
        }

        with(metaItemDto.title) {
            if (isEmpty())
                error = "Title can not be empty"
            else if (length < titleMinLength)
                error = "Title length can not be shorter than $titleMinLength symbols"
            else if (length > titleMaxLength)
                error = "Title length can not be longer than $titleMaxLength symbols"
        }

        with(metaItemDto.description) {
            if (isEmpty()) {
                error = "Description can not be empty"
            } else if (length < descriptionMinLength) {
                error =
                    "Description length can not be shorter than $descriptionMinLength symbols"
            } else if (length > descriptionMaxLength) {
                error =
                    "Description length can not be longer than $descriptionMaxLength symbols"
            }
        }

        metaItemDto.author[MetaItem.USER_UID]?.let {
            if (it.isEmpty())
                error = "Can't find author uid"
        }

        metaItemDto.dndClass[MetaItem.NAME]?.let {
            if (it.isEmpty())
                error = "Class can not be empty"
        }

        if (error.isNotEmpty()) {
            _metaItemState.value = MetaItemState.Error(error)
            return false
        }

        return true
    }

    companion object {
        const val META_BUILDS = "builds"
    }

}
