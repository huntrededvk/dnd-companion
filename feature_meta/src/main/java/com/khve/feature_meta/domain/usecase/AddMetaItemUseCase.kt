package com.khve.feature_meta.domain.usecase

import com.khve.feature_meta.domain.entity.MetaItem
import com.khve.feature_meta.domain.entity.MetaItemState
import com.khve.feature_meta.domain.repository.MetaListRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AddMetaItemUseCase @Inject constructor(
    private val repository: MetaListRepository
) {
    operator fun invoke(metaItem: MetaItem): StateFlow<MetaItemState> {
        return repository.addMetaItem(metaItem)
    }
}
