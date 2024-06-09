package com.khve.dndcompanion.data.meta

import com.khve.dndcompanion.domain.meta.entity.MetaItem
import com.khve.dndcompanion.domain.meta.repository.MetaListRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MetaListRepositoryImpl @Inject constructor () : MetaListRepository {
    override suspend fun addMetaItem(metaItem: MetaItem) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMetaItem(metaItem: MetaItem) {
        TODO("Not yet implemented")
    }

    override suspend fun editMetaItem(metaItem: MetaItem) {
        TODO("Not yet implemented")
    }

    override suspend fun getMetaItem(metaItemId: Int): StateFlow<MetaItem> {
        TODO("Not yet implemented")
    }

    override fun getMetaList(): StateFlow<List<MetaItem>> {
        TODO("Not yet implemented")
    }
}