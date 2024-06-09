package com.khve.dndcompanion.domain.meta.usecase

import com.khve.dndcompanion.domain.meta.entity.MetaItem
import com.khve.dndcompanion.domain.meta.repository.MetaListRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetMetaListUseCase  @Inject constructor(
    private val metaListRepository: MetaListRepository
) {

    fun getMetaList(): StateFlow<List<MetaItem>> {
        return metaListRepository.getMetaList()
    }
}