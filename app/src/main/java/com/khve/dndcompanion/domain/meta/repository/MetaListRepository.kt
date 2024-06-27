package com.khve.dndcompanion.domain.meta.repository

import com.khve.dndcompanion.domain.meta.entity.PartySizeEnum
import com.khve.dndcompanion.domain.meta.entity.MetaCardListState
import com.khve.dndcompanion.domain.meta.entity.MetaItem
import com.khve.dndcompanion.domain.meta.entity.MetaItemState
import kotlinx.coroutines.flow.StateFlow

interface MetaListRepository {
    fun getMetaCardList(
        partySize: PartySizeEnum
    ): StateFlow<MetaCardListState>

    fun addMetaItem(metaItemDto: MetaItem): StateFlow<MetaItemState>
    fun getMetaItem(
        metaItemUid: String,
        partySize: PartySizeEnum
    ): StateFlow<MetaItemState>

    fun deleteMetaItem(metaItem: MetaItem): StateFlow<MetaItemState>
    fun updateMetaItem(metaItem: MetaItem): StateFlow<MetaItemState>
}
