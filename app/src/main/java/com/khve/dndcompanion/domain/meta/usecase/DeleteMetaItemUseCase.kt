package com.khve.dndcompanion.domain.meta.usecase

import com.khve.dndcompanion.domain.meta.entity.MetaItem
import com.khve.dndcompanion.domain.meta.repository.MetaListRepository
import javax.inject.Inject

class DeleteMetaItemUseCase @Inject constructor(
    private val metaListRepository: MetaListRepository
) {

    suspend fun deleteMetaItem(metaItem: MetaItem) {
        metaListRepository.deleteMetaItem(metaItem)
    }
}