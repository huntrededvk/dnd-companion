package com.khve.feature_meta.data.repository

import com.khve.feature_meta.data.network.firebase.FirebaseMetaManager
import com.khve.feature_meta.domain.entity.MetaCardListState
import com.khve.feature_meta.domain.entity.MetaItem
import com.khve.feature_meta.domain.entity.MetaItemState
import com.khve.feature_meta.domain.entity.PartySizeEnum
import com.khve.feature_meta.domain.repository.MetaListRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MetaListRepositoryImpl @Inject constructor(
    private val metaManager: FirebaseMetaManager
) : MetaListRepository {

    override fun getMetaCardList(
        partySize: PartySizeEnum
    ): StateFlow<MetaCardListState> = metaManager.getMetaCardList(partySize)

    override fun addMetaItem(metaItemDto: MetaItem): StateFlow<MetaItemState> =
        metaManager.addMetaItem(metaItemDto)

    override fun getMetaItem(
        metaItemUid: String,
        partySize: PartySizeEnum
    ): StateFlow<MetaItemState> =
        metaManager.getMetaItem(metaItemUid, partySize)

    override fun deleteMetaItem(metaItem: MetaItem): StateFlow<MetaItemState> =
        metaManager.deleteMetaItem(metaItem)

    override fun updateMetaItem(metaItem: MetaItem): StateFlow<MetaItemState> =
        metaManager.updateMetaItem(metaItem)

}
