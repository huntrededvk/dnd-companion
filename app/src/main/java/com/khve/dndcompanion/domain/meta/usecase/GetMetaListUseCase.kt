package com.khve.dndcompanion.domain.meta.usecase

import com.khve.dndcompanion.domain.meta.entity.MetaListState
import com.khve.dndcompanion.domain.meta.repository.MetaListRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetMetaListUseCase  @Inject constructor(
    private val metaListRepository: MetaListRepository
) {

    operator fun invoke(): StateFlow<MetaListState> {
        return metaListRepository.getMetaList()
    }
}