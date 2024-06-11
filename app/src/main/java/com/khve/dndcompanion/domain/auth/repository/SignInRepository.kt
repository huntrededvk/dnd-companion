package com.khve.dndcompanion.domain.auth.repository

import com.khve.dndcompanion.domain.auth.entity.UserState
import kotlinx.coroutines.flow.StateFlow

interface SignInRepository {

    fun signInWithEmailAndPassword(email: String, password: String): StateFlow<UserState>

}