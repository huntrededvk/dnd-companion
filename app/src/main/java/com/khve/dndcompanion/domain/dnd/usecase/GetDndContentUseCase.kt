package com.khve.dndcompanion.domain.dnd.usecase

import com.khve.dndcompanion.domain.dnd.entity.DndContentState
import com.khve.dndcompanion.domain.dnd.repository.DndRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class GetDndContentUseCase @Inject constructor(
    private val repository: DndRepository
){
    suspend operator fun invoke(): StateFlow<DndContentState> {
        return repository.getDndContent()
    }
}