package com.khve.dndcompanion.domain.meta.usecase

import com.khve.dndcompanion.domain.meta.entity.MetaItem
import com.khve.dndcompanion.domain.meta.repository.MetaListRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetMetaItemUseCase @Inject constructor(
    private val metaListRepository: MetaListRepository
) {

    suspend fun getMetaItem(metaItemId: Int): StateFlow<MetaItem> {
        return metaListRepository.getMetaItem(metaItemId)
    }
}