package com.khve.dndcompanion.data.auth.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.auth.repository.SignInRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SignInRepositoryImpl @Inject constructor() : SignInRepository {

    private val auth = Firebase.auth


    override fun signInWithEmailAndPassword(email: String, password: String) {
        auth.signInWithEmailAndPassword(email.trim(), password.trim())
    }

}