package com.khve.feature_auth.domain.repository

import com.khve.feature_auth.data.model.UserSignUpDto
import com.khve.feature_auth.domain.entity.AuthState
import kotlinx.coroutines.flow.StateFlow

interface SingUpRepository {

    fun createUser(userSignUpDto: UserSignUpDto): StateFlow<AuthState>
}
