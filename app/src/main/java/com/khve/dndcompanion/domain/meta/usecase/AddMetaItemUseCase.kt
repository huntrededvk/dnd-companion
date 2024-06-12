package com.khve.dndcompanion.domain.meta.usecase

import com.khve.dndcompanion.data.meta.model.MetaItemDto
import com.khve.dndcompanion.domain.meta.entity.MetaItemState
import com.khve.dndcompanion.domain.meta.repository.MetaListRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AddMetaItemUseCase @Inject constructor(
    private val repository: MetaListRepository
) {
    operator fun invoke(metaItemDto: MetaItemDto): StateFlow<MetaItemState> {
        return repository.addMetaItem(metaItemDto)
    }
}