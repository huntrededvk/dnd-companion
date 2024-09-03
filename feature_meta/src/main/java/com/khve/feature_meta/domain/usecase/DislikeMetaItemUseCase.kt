package com.khve.feature_meta.domain.usecase

import com.khve.feature_meta.domain.entity.MetaItem
import com.khve.feature_meta.domain.repository.MetaListRepository
import javax.inject.Inject

class DislikeMetaItemUseCase @Inject constructor(
    private val repository: MetaListRepository
) {
    operator fun invoke(metaItem: MetaItem) {
        repository.dislike(metaItem)
    }
}
