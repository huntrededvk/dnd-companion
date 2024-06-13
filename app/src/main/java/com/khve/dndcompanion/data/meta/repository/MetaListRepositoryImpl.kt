package com.khve.dndcompanion.data.meta.repository

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.khve.dndcompanion.data.meta.mapper.MetaMapper
import com.khve.dndcompanion.data.meta.model.MetaItemDto
import com.khve.dndcompanion.data.network.FirebaseMetaManager
import com.khve.dndcompanion.domain.meta.entity.MetaItem
import com.khve.dndcompanion.domain.meta.entity.MetaItemState
import com.khve.dndcompanion.domain.meta.entity.MetaListState
import com.khve.dndcompanion.domain.meta.repository.MetaListRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class MetaListRepositoryImpl @Inject constructor (
    private val metaManager: FirebaseMetaManager
) : MetaListRepository {

    override fun getMetaList(): StateFlow<MetaListState> {
        return metaManager.getMetaList()
    }

    override fun addMetaItem(metaItemDto: MetaItemDto): StateFlow<MetaItemState> {
        return metaManager.addMetaItem(metaItemDto)
    }

    override fun getMetaItem(metaItemUid: String): StateFlow<MetaItemState> {
        return metaManager.getMetaItem(metaItemUid)
    }
}