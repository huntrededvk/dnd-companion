package com.khve.dndcompanion.domain.meta.repository

import com.khve.dndcompanion.domain.meta.entity.MetaCardListState
import com.khve.dndcompanion.domain.meta.entity.MetaItem
import com.khve.dndcompanion.domain.meta.entity.MetaItemState
import kotlinx.coroutines.flow.StateFlow

interface MetaListRepository {
    fun getMetaList(): StateFlow<MetaCardListState>
    fun addMetaItem(metaItemDto: MetaItem): StateFlow<MetaItemState>
    fun getMetaItem(metaItemUid: String): StateFlow<MetaItemState>
    fun deleteMetaItem(metaItem: MetaItem): StateFlow<MetaItemState>
    fun updateMetaItem(metaItem: MetaItem): StateFlow<MetaItemState>
}
