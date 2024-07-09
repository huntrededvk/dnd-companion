package com.khve.feature_auth.data.repository

import com.khve.feature_auth.data.network.firebase.FirebaseUserManager
import com.khve.feature_auth.domain.entity.AuthState
import com.khve.feature_auth.domain.repository.SignInRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val userManager: FirebaseUserManager
) : SignInRepository {


    override fun signInWithEmailAndPassword(email: String, password: String): StateFlow<AuthState> =
        userManager.signInWithEmailAndPassword(email, password)

}
