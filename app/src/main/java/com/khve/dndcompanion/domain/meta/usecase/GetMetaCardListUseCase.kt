package com.khve.dndcompanion.domain.meta.usecase

import com.khve.dndcompanion.domain.meta.entity.MetaCardListState
import com.khve.dndcompanion.domain.meta.entity.PartySizeEnum
import com.khve.dndcompanion.domain.meta.repository.MetaListRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetMetaCardListUseCase @Inject constructor(
    private val metaListRepository: MetaListRepository
) {

    operator fun invoke(
        partySize: PartySizeEnum
    ): StateFlow<MetaCardListState> {
        return metaListRepository.getMetaCardList(partySize)
    }
}
