package com.khve.dndcompanion.domain.meta.usecase

import com.khve.dndcompanion.domain.meta.entity.MetaBuildEnum
import com.khve.dndcompanion.domain.meta.entity.MetaCardListState
import com.khve.dndcompanion.domain.meta.entity.MetaTypeEnum
import com.khve.dndcompanion.domain.meta.repository.MetaListRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetMetaCardListUseCase @Inject constructor(
    private val metaListRepository: MetaListRepository
) {

    operator fun invoke(
        metaType: MetaTypeEnum,
        metaBuild: MetaBuildEnum
    ): StateFlow<MetaCardListState> {
        return metaListRepository.getMetaCardList(metaType, metaBuild)
    }
}
