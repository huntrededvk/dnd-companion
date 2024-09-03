package com.khve.feature_main.domain.repository

import com.khve.feature_auth.domain.entity.UserState
import kotlinx.coroutines.flow.StateFlow

interface MainRepository {

    fun getCurrentUserFromDb(): StateFlow<UserState>

}
