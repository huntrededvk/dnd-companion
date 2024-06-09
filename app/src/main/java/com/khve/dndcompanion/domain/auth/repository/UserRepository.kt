package com.khve.dndcompanion.domain.auth.repository

import com.khve.dndcompanion.data.auth.model.UserSignUpDto
import com.khve.dndcompanion.domain.auth.entity.UserState
import kotlinx.coroutines.flow.StateFlow

interface UserRepository {

    fun createUser(userSignUpDto: UserSignUpDto): StateFlow<UserState>
    fun deleteCurrentUser(): StateFlow<UserState>
}