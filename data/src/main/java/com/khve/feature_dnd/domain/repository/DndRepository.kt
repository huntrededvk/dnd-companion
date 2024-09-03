package com.khve.feature_dnd.domain.repository

import com.khve.feature_dnd.domain.entity.DndContentState
import kotlinx.coroutines.flow.StateFlow

interface DndRepository {
    suspend fun getDndContent(): StateFlow<DndContentState>
}
