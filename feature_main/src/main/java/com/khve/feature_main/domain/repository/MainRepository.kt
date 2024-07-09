package com.khve.feature_main.domain.repository

import kotlinx.coroutines.flow.StateFlow

interface MainRepository {

    fun getCurrentUserFromDb(): StateFlow<com.khve.feature_auth.domain.entity.UserState>

}
