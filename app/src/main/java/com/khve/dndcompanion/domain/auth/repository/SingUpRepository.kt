package com.khve.dndcompanion.domain.auth.repository

import com.khve.dndcompanion.data.auth.model.UserSignUpDto
import com.khve.dndcompanion.domain.auth.entity.AuthState
import kotlinx.coroutines.flow.StateFlow

interface SingUpRepository {

    fun createUser(userSignUpDto: UserSignUpDto): StateFlow<AuthState>
}