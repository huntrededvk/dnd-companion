package com.khve.feature_auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khve.feature_auth.data.model.UserSignUpDto
import com.khve.feature_auth.domain.entity.AuthState
import com.khve.feature_auth.domain.usecase.CreateUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val createUserUseCase: CreateUserUseCase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState = _authState.asStateFlow()

    fun createUser(userSignUpDto: UserSignUpDto) {
        viewModelScope.launch {
            _authState.value = AuthState.Progress
            createUserUseCase(userSignUpDto).collect {
                _authState.value = it
            }
        }
    }
}
