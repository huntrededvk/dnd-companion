package com.khve.dndcompanion.data.meta.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.khve.dndcompanion.data.meta.mapper.MetaMapper
import com.khve.dndcompanion.data.meta.model.MetaItemDto
import com.khve.dndcompanion.domain.meta.entity.MetaItem
import com.khve.dndcompanion.domain.meta.entity.MetaItemState
import com.khve.dndcompanion.domain.meta.entity.MetaListState
import com.khve.dndcompanion.domain.meta.repository.MetaListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MetaListRepositoryImpl @Inject constructor (
    private val metaMapper: MetaMapper
) : MetaListRepository {

    private val mDocRef = FirebaseFirestore.getInstance().collection("meta")

    private val _metaListState = MutableStateFlow<MetaListState>(MetaListState.Initial)
    private val metaListState = _metaListState.asStateFlow()

    private val _metaItemState = MutableStateFlow<MetaItemState>(MetaItemState.Initial)
    private val metaItemState = _metaItemState.asStateFlow()

    override fun getMetaList(): StateFlow<MetaListState> {
        mDocRef.get()
            .addOnSuccessListener { snapshot ->
                val metaList = mutableListOf<MetaItem>()
                val snapshotList = snapshot?.documents ?: emptyList()
                for (currentSnapshot in snapshotList) {
                    val retrievedMetaItemDto = currentSnapshot?.toObject(MetaItemDto::class.java)
                    if (retrievedMetaItemDto != null) {
                        metaList.add(
                            metaMapper.mapMetaItemDtoToMetaItem(retrievedMetaItemDto)
                        )
                    }
                }
                if (metaList.isEmpty()) {
                    _metaListState.value = MetaListState.Error("We don't have any meta yet")
                } else {
                    _metaListState.value = MetaListState.MetaList(metaList)
                }
            }
            .addOnFailureListener {
                _metaListState.value = MetaListState.Error(
                    it.localizedMessage ?: "Unknown error"
                )
            }

        return metaListState
    }

    override fun addMetaItem(metaItemDto: MetaItemDto): StateFlow<MetaItemState> {
        if (!isValidated(metaItemDto))
            return metaItemState

        mDocRef.document()
            .set(metaItemDto)
            .addOnSuccessListener {
                updateMetaListState(metaItemDto)
                _metaItemState.value = MetaItemState.Success
            }
            .addOnFailureListener { e ->
                _metaItemState.value = MetaItemState.Error(e.localizedMessage ?: "Unknown error")
            }

        return metaItemState
    }

    private fun updateMetaListState(metaItemDto: MetaItemDto) {
        (_metaListState.value as? MetaListState.MetaList)?.let { currentState ->
            val updatedList = currentState.metaList.toMutableList().apply {
                add(metaMapper.mapMetaItemDtoToMetaItem(metaItemDto))
            }
            _metaListState.value = MetaListState.MetaList(updatedList)
        }
    }

    private fun isValidated(metaItemDto: MetaItemDto): Boolean {
        var error = ""

        metaItemDto.title.let {
            if (it.isEmpty())
                error = "Title can not be empty"
            else if (it.length < 3)
                error = "Title is too short"
            else if (it.length > 200)
                error = "Title is too long"
        }

        metaItemDto.authorUid.let {
            if (it.isEmpty())
                error = "Can't find author uid"
        }

        if (error.isNotEmpty()) {
            _metaItemState.value = MetaItemState.Error(error)
            return false
        }

        return true
    }

    override fun getMetaItem(metaItemUid: String): StateFlow<MetaItemState> {
        mDocRef.document(metaItemUid).get()
            .addOnSuccessListener { snapshot ->
                val metaItemDto = snapshot?.toObject(MetaItemDto::class.java)
                _metaItemState.value = if (metaItemDto != null) {
                    MetaItemState.MetaItem(metaMapper.mapMetaItemDtoToMetaItem(metaItemDto))
                } else {
                    MetaItemState.Error("User's data is empty")
                }
            }
            .addOnFailureListener {
                _metaItemState.value = MetaItemState.Error(
                    it.localizedMessage ?: "Unknown error"
                )
            }

        return metaItemState
    }
}