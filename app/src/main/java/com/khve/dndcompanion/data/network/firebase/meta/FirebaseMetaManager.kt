package com.khve.dndcompanion.data.network.firebase.meta

import com.google.firebase.firestore.FirebaseFirestore
import com.khve.dndcompanion.data.meta.mapper.MetaMapper
import com.khve.dndcompanion.data.meta.model.MetaCardItemDto
import com.khve.dndcompanion.data.meta.model.MetaItemDto
import com.khve.dndcompanion.data.network.firebase.auth.FirebaseUserManager
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.meta.entity.PartySizeEnum
import com.khve.dndcompanion.domain.meta.entity.MetaCardItem
import com.khve.dndcompanion.domain.meta.entity.MetaCardListState
import com.khve.dndcompanion.domain.meta.entity.MetaItem
import com.khve.dndcompanion.domain.meta.entity.MetaItemState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

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

    fun getMetaCardList(
        partySize: PartySizeEnum
    ): StateFlow<MetaCardListState> {
        _metaCardListState.value = MetaCardListState.Initial
        mDocRef.document(META_BUILDS).collection(partySize.name).get()
            .addOnSuccessListener { snapshot ->
                val metaList = mutableListOf<MetaCardItem>()
                val snapshotList = snapshot?.documents ?: emptyList()
                for (currentSnapshot in snapshotList) {
                    val retrievedMetaItem = currentSnapshot
                        ?.toObject(MetaCardItemDto::class.java)
                    if (retrievedMetaItem != null) {
                        metaList.add(
                            metaMapper.metaCardItemDtoToMetaCardItem(
                                retrievedMetaItem.copy(uid = currentSnapshot.id)
                            )
                        )
                    }
                }
                if (metaList.isEmpty()) {
                    _metaCardListState.value = MetaCardListState.Error(
                        "We don't have any meta yet"
                    )
                } else {
                    _metaCardListState.value = MetaCardListState.MetaCardList(metaList)
                }
            }
            .addOnFailureListener {
                _metaCardListState.value = MetaCardListState.Error(
                    it.localizedMessage ?: "Unknown error"
                )
            }

        return metaCardListState
    }

    fun addMetaItem(metaItem: MetaItem): StateFlow<MetaItemState> {
        val mappedMetaItemDto = metaMapper.metaItemToMetaItemDto(metaItem)
        if (!isValidated(
                metaItemDto = mappedMetaItemDto,
                titleMinLength = 3,
                titleMaxLength = 200,
                descriptionMinLength = 10,
                descriptionMaxLength = 10000
            )
        )
            return metaItemState

        mDocRef.document(META_BUILDS).collection(
            metaItem.partySize?.name ?:
            throw IllegalArgumentException("Can not add meta item, party size is null")
        ).document()
            .set(mappedMetaItemDto)
            .addOnSuccessListener {
                updateMetaListState(mappedMetaItemDto)
                _metaItemState.value = MetaItemState.Success
            }
            .addOnFailureListener { e ->
                _metaItemState.value = MetaItemState.Error(
                    e.localizedMessage ?: "Unknown error"
                )
            }

        return metaItemState
    }

    private fun updateMetaListState(metaItemDto: MetaItemDto) {
        (_metaCardListState.value as? MetaCardListState.MetaCardList)?.let { currentState ->
            val updatedList = currentState.metaCardList.toMutableList().apply {
                add(
                    metaMapper.metaItemDtoToMetaCardItem(metaItemDto)
                )
            }
            _metaCardListState.value = MetaCardListState.MetaCardList(updatedList)
        }
    }

    fun deleteMetaItem(metaItem: MetaItem): StateFlow<MetaItemState> {
        val currentUser = userManager.getCurrentDbUserState()

        if (currentUser is UserState.User &&
            currentUser.user.uid == metaItem.author[MetaItemDto.USER_UID]
        ) {
            mDocRef.document(META_BUILDS).collection(
                metaItem.partySize?.name ?:
                throw IllegalArgumentException("Can not delete meta item, party size is null")
            )
                .document(metaItem.uid).delete()
                .addOnSuccessListener {
                    _metaItemState.value = MetaItemState.Success
                }
                .addOnFailureListener {
                    _metaItemState.value = MetaItemState.Error(
                        it.localizedMessage ?: "Couldn't delete meta data"
                    )
                }
        } else if (currentUser is UserState.Error) {
            _metaItemState.value = MetaItemState.Error(currentUser.errorMessage)
        }

        return metaItemState
    }

    fun updateMetaItem(metaItem: MetaItem): StateFlow<MetaItemState> {
        val currentUser = userManager.getCurrentDbUserState()
        val mappedMetaItemDto = metaMapper.metaItemToMetaItemDto(metaItem)

        if (currentUser is UserState.User &&
            currentUser.user.uid == mappedMetaItemDto.author[MetaItemDto.USER_UID]
        ) {
            mDocRef.document(META_BUILDS).collection(
                metaItem.partySize?.name ?:
                throw IllegalArgumentException("Can not update meta item, party size is null")
            )
                .document(mappedMetaItemDto.uid).set(mappedMetaItemDto)
                .addOnSuccessListener {
                    _metaItemState.value = MetaItemState.Success
                }
                .addOnFailureListener {
                    _metaItemState.value = MetaItemState.Error(
                        it.localizedMessage ?: "Couldn't delete meta data"
                    )
                }
        } else if (currentUser is UserState.Error) {
            _metaItemState.value = MetaItemState.Error(currentUser.errorMessage)
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

        with(metaItemDto.title) {
            if (isEmpty())
                error = "Title can not be empty"
            else if (length < titleMinLength)
                error = "Title is too short"
            else if (length > titleMaxLength)
                error = "Title is too long"
        }

        with(metaItemDto.description) {
            if (isEmpty()) {
                error = "Description can not be empty"
            } else if (length < descriptionMinLength) {
                error = "Description is too short"
            } else if (length > descriptionMaxLength) {
                error = "Description is too long"
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
