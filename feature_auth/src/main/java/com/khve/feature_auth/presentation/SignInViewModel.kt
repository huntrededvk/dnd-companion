package com.khve.feature_auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInWithEmailAndPasswordUseCase: com.khve.feature_auth.domain.usercase.SignInWithEmailAndPasswordUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<com.khve.feature_auth.domain.entity.AuthState>(com.khve.feature_auth.domain.entity.AuthState.Initial)
    val authState = _authState.asStateFlow()


    fun signInWithEmailAndPassword(email: String, password: String) {
        viewModelScope.launch {
            _authState.value = com.khve.feature_auth.domain.entity.AuthState.Progress
            signInWithEmailAndPasswordUseCase(email, password).collect {
                _authState.value = it
            }
        }
    }

}
