package com.khve.dndcompanion.domain.auth.usercase

import com.khve.dndcompanion.domain.auth.entity.AuthState
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.auth.repository.SignInRepository
import kotlinx.coroutines.flow.StateFlow
import java.lang.Exception
import javax.inject.Inject

class SignInWithEmailAndPasswordUseCase @Inject constructor(
    private val repository: SignInRepository
) {
    operator fun invoke(email: String, password: String): StateFlow<AuthState> {
        return repository.signInWithEmailAndPassword(email, password)
    }
}