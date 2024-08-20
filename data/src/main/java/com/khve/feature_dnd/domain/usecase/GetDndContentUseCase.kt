package com.khve.feature_dnd.domain.usecase

import com.khve.feature_dnd.domain.entity.DndContentState
import com.khve.feature_dnd.domain.repository.DndRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetDndContentUseCase @Inject constructor(
    private val repository: DndRepository
) {
    suspend operator fun invoke(): StateFlow<DndContentState> {
        return repository.getDndContent()
    }
}
