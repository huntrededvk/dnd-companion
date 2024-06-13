package com.khve.dndcompanion.data.auth.repository

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.khve.dndcompanion.data.network.FirebaseUserManager
import com.khve.dndcompanion.domain.auth.entity.AuthState
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.auth.repository.SignInRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.lang.Exception
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor(
    private val userManager: FirebaseUserManager
) : SignInRepository {


    override fun signInWithEmailAndPassword(email: String, password: String): StateFlow<AuthState> {
        return userManager.signInWithEmailAndPassword(email, password)
    }

}