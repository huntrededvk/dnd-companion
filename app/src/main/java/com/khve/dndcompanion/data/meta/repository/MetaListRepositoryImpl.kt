package com.khve.dndcompanion.data.meta.repository

import com.khve.dndcompanion.data.network.firebase.meta.FirebaseMetaManager
import com.khve.dndcompanion.domain.meta.entity.MetaCardListState
import com.khve.dndcompanion.domain.meta.entity.MetaItem
import com.khve.dndcompanion.domain.meta.entity.MetaItemState
import com.khve.dndcompanion.domain.meta.entity.PartySizeEnum
import com.khve.dndcompanion.domain.meta.repository.MetaListRepository
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
