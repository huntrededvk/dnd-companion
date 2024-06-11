package com.khve.dndcompanion.domain.repository

import com.khve.dndcompanion.domain.auth.entity.UserState
import kotlinx.coroutines.flow.StateFlow

interface MainRepository {

    fun getCurrentUserFromDb(): StateFlow<UserState>

}