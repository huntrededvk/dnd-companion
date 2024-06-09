package com.khve.dndcompanion.domain.meta.repository

import com.khve.dndcompanion.domain.meta.entity.MetaItem
import kotlinx.coroutines.flow.StateFlow

interface MetaListRepository {
    suspend fun addMetaItem(metaItem: MetaItem)

    suspend fun deleteMetaItem(metaItem: MetaItem)

    suspend fun editMetaItem(metaItem: MetaItem)

    suspend fun getMetaItem(metaItemId: Int): StateFlow<MetaItem>

    fun getMetaList(): StateFlow<List<MetaItem>>

}