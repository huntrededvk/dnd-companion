package com.khve.feature_auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUserUseCase: com.khve.feature_auth.domain.usercase.CreateUserUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<com.khve.feature_auth.domain.entity.AuthState>(com.khve.feature_auth.domain.entity.AuthState.Initial)
    val authState = _authState.asStateFlow()

    fun createUser(userSignUpDto: com.khve.feature_auth.data.model.UserSignUpDto) {
        viewModelScope.launch {
            _authState.value = com.khve.feature_auth.domain.entity.AuthState.Progress
            createUserUseCase(userSignUpDto).collect {
                _authState.value = it
            }
        }
    }
}
