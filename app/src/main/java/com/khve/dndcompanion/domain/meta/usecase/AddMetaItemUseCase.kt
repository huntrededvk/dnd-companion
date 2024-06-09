package com.khve.dndcompanion.domain.meta.usecase

import com.khve.dndcompanion.domain.meta.entity.MetaItem
import com.khve.dndcompanion.domain.meta.repository.MetaListRepository
import javax.inject.Inject

class AddMetaItemUseCase @Inject constructor(
    private val metaListRepository: MetaListRepository
) {

    suspend fun addMetaItem(metaItem: MetaItem) {
        metaListRepository.addMetaItem(metaItem)
    }
}