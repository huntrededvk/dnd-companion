package com.khve.feature_auth.domain.repository

import com.khve.feature_auth.domain.entity.AuthState
import kotlinx.coroutines.flow.StateFlow

interface SignInRepository {

    fun signInWithEmailAndPassword(email: String, password: String): StateFlow<AuthState>

}
