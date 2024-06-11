package com.khve.dndcompanion.data.auth.repository

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.khve.dndcompanion.domain.auth.entity.AuthState
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.auth.repository.SignInRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.lang.Exception
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor() : SignInRepository {

    private val auth = Firebase.auth
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState = _authState.asStateFlow()


    override fun signInWithEmailAndPassword(email: String, password: String): StateFlow<AuthState> {
        auth.signInWithEmailAndPassword(email.trim(), password.trim())
            .addOnFailureListener {
                _authState.value = AuthState.Error(it.localizedMessage ?: "Unknown error")
            }

        return authState
    }

}