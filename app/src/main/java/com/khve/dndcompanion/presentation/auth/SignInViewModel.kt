package com.khve.dndcompanion.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.khve.dndcompanion.data.auth.repository.SignInRepositoryImpl
import com.khve.dndcompanion.domain.auth.entity.AuthState
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.auth.usercase.SignInWithEmailAndPasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    private val signInWithEmailAndPasswordUseCase: SignInWithEmailAndPasswordUseCase
): ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState = _authState.asStateFlow()


    fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            signInWithEmailAndPasswordUseCase(email, password).collect {
                _authState.value = it
            }
        }
    }

}