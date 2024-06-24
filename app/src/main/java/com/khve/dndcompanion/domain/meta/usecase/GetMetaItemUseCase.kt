package com.khve.dndcompanion.domain.meta.usecase

import com.khve.dndcompanion.domain.meta.entity.MetaItemState
import com.khve.dndcompanion.domain.meta.repository.MetaListRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetMetaItemUseCase @Inject constructor(
    private val repository: MetaListRepository
) {
    operator fun invoke(metaItemUid: String): StateFlow<MetaItemState> {
        return repository.getMetaItem(metaItemUid)
    }
}
