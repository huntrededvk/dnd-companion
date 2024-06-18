package com.khve.dndcompanion.data.auth.repository

import com.khve.dndcompanion.data.network.firebase.FirebaseUserManager
import com.khve.dndcompanion.domain.auth.entity.AuthState
import com.khve.dndcompanion.domain.auth.repository.SignInRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val userManager: FirebaseUserManager
) : SignInRepository {


    override fun signInWithEmailAndPassword(email: String, password: String): StateFlow<AuthState> =
        userManager.signInWithEmailAndPassword(email, password)

}