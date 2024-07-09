package com.khve.feature_auth.domain.usercase

import com.khve.feature_auth.domain.entity.AuthState
import com.khve.feature_auth.domain.repository.SignInRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SignInWithEmailAndPasswordUseCase @Inject constructor(
    private val repository: SignInRepository
) {
    operator fun invoke(email: String, password: String): StateFlow<AuthState> {
        return repository.signInWithEmailAndPassword(email, password)
    }
}
