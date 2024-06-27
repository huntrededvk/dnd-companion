package com.khve.dndcompanion.data.meta.repository

import com.khve.dndcompanion.data.network.firebase.meta.FirebaseMetaManager
import com.khve.dndcompanion.domain.meta.entity.MetaBuildEnum
import com.khve.dndcompanion.domain.meta.entity.MetaCardListState
import com.khve.dndcompanion.domain.meta.entity.MetaItem
import com.khve.dndcompanion.domain.meta.entity.MetaItemState
import com.khve.dndcompanion.domain.meta.entity.MetaTypeEnum
import com.khve.dndcompanion.domain.meta.repository.MetaListRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MetaListRepositoryImpl @Inject constructor(
    private val metaManager: FirebaseMetaManager
) : MetaListRepository {

    override fun getMetaCardList(
        metaType: MetaTypeEnum,
        metaBuild: MetaBuildEnum
    ): StateFlow<MetaCardListState> = metaManager.getMetaCardList(metaType, metaBuild)

    override fun addMetaItem(metaItemDto: MetaItem): StateFlow<MetaItemState> =
        metaManager.addMetaItem(metaItemDto)

    override fun getMetaItem(
        metaItemUid: String,
        metaType: MetaTypeEnum,
        metaBuild: MetaBuildEnum
    ): StateFlow<MetaItemState> =
        metaManager.getMetaItem(metaItemUid, metaType, metaBuild)

    override fun deleteMetaItem(metaItem: MetaItem): StateFlow<MetaItemState> =
        metaManager.deleteMetaItem(metaItem)

    override fun updateMetaItem(metaItem: MetaItem): StateFlow<MetaItemState> =
        metaManager.updateMetaItem(metaItem)

}
