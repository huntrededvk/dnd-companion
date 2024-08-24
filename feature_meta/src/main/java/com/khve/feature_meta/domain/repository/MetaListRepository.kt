package com.khve.feature_meta.domain.repository

import com.khve.feature_meta.domain.entity.MetaCardListState
import com.khve.feature_meta.domain.entity.MetaItem
import com.khve.feature_meta.domain.entity.MetaItemState
import com.khve.feature_meta.domain.entity.PartySizeEnum
import kotlinx.coroutines.flow.StateFlow

interface MetaListRepository {
    fun getMetaCardList(
        partySize: PartySizeEnum
    ): StateFlow<MetaCardListState>

    fun addMetaItem(metaItemDto: MetaItem): StateFlow<MetaItemState>
    fun getMetaItem(metaItemUid: String): StateFlow<MetaItemState>

    fun like(metaItem: MetaItem)
    fun dislike(metaItem: MetaItem)
    fun deleteMetaItem(metaItem: MetaItem): StateFlow<MetaItemState>
    fun updateMetaItem(metaItem: MetaItem): StateFlow<MetaItemState>
}
