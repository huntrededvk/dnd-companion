package com.khve.dndcompanion.domain.dnd.repository

import com.khve.dndcompanion.domain.dnd.entity.DndContentState
import kotlinx.coroutines.flow.StateFlow

interface DndRepository {
    suspend fun getDndContent(): StateFlow<DndContentState>
}