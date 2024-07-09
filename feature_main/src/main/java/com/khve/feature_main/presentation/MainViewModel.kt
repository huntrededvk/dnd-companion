package com.khve.feature_main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khve.feature_auth.domain.entity.UserState
import com.khve.feature_main.domain.usecase.GetCurrentUserUseCase
import com.khve.feature_main.util.ConnectionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCurrentUserUseCase: GetCurrentUserUseCase,
    private val connectionManager: ConnectionManager
) : ViewModel() {

    private val _userState = MutableStateFlow<UserState>(UserState.Initial)
    val userState = _userState.asStateFlow()

    private val _internetState = MutableStateFlow(true)
    val internetState = _internetState.asStateFlow()

    init {
            getCurrentInternetConnection()
            getCurrentUser()
    }

    private fun getCurrentInternetConnection() {
        viewModelScope.launch {
            connectionManager.isConnected.collect {
                _internetState.value = it
            }
        }
    }

    fun getCurrentUser() {
        if (_internetState.value) {
            viewModelScope.launch {
                getCurrentUserUseCase().collect {
                    _userState.value = it
                }
            }
        }
    }

}
