package com.khve.dndcompanion.domain.meta.repository

import com.khve.dndcompanion.domain.meta.entity.MetaBuildEnum
import com.khve.dndcompanion.domain.meta.entity.MetaCardListState
import com.khve.dndcompanion.domain.meta.entity.MetaItem
import com.khve.dndcompanion.domain.meta.entity.MetaItemState
import com.khve.dndcompanion.domain.meta.entity.MetaTypeEnum
import kotlinx.coroutines.flow.StateFlow

interface MetaListRepository {
    fun getMetaCardList(
        metaType: MetaTypeEnum,
        metaBuild: MetaBuildEnum
    ): StateFlow<MetaCardListState>

    fun addMetaItem(metaItemDto: MetaItem): StateFlow<MetaItemState>
    fun getMetaItem(
        metaItemUid: String,
        metaType: MetaTypeEnum,
        metaBuild: MetaBuildEnum
    ): StateFlow<MetaItemState>

    fun deleteMetaItem(metaItem: MetaItem): StateFlow<MetaItemState>
    fun updateMetaItem(metaItem: MetaItem): StateFlow<MetaItemState>
}
