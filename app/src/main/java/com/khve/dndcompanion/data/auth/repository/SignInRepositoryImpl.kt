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

    private val _userState = MutableStateFlow<UserState>(UserState.Initial)
    private val userState = _userState.asStateFlow()
    override fun signInWithEmailAndPassword(email: String, password: String): StateFlow<UserState> {
        auth.signInWithEmailAndPassword(email.trim(), password.trim())
            .addOnSuccessListener {
                _userState.value = UserState.Authorized
            } .addOnFailureListener {
                _userState.value = UserState.Error(it.message.toString())
                _userState.value = UserState.Initial
            }
        return userState
    }

}