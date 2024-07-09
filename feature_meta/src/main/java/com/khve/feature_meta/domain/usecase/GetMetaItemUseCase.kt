package com.khve.feature_meta.domain.usecase

import com.khve.feature_meta.domain.entity.MetaItemState
import com.khve.feature_meta.domain.entity.PartySizeEnum
import com.khve.feature_meta.domain.repository.MetaListRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetMetaItemUseCase @Inject constructor(
    private val repository: MetaListRepository
) {
    operator fun invoke(
        metaItemUid: String,
        partySize: PartySizeEnum
    ): StateFlow<MetaItemState> {
        return repository.getMetaItem(metaItemUid, partySize)
    }
}
