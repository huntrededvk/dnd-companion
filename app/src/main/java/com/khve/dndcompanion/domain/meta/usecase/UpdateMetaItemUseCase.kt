package com.khve.dndcompanion.domain.meta.usecase

import com.khve.dndcompanion.domain.meta.entity.MetaItem
import com.khve.dndcompanion.domain.meta.entity.MetaItemState
import com.khve.dndcompanion.domain.meta.repository.MetaListRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class UpdateMetaItemUseCase @Inject constructor(
    private val repository: MetaListRepository
) {
    operator fun invoke(metaItem: MetaItem): StateFlow<MetaItemState> {
        return repository.updateMetaItem(metaItem)
    }
}
