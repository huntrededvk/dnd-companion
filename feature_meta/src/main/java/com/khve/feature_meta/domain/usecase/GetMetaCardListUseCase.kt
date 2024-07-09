package com.khve.feature_meta.domain.usecase

import com.khve.feature_meta.domain.entity.MetaCardListState
import com.khve.feature_meta.domain.entity.PartySizeEnum
import com.khve.feature_meta.domain.repository.MetaListRepository
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
