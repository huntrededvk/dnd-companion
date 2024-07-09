package com.khve.feature_main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khve.feature_auth.data.network.firebase.FirebaseUserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainFragmentViewModel @Inject constructor(
    private val userManager: FirebaseUserManager
) : ViewModel() {

    private val _currentUser = MutableStateFlow<com.khve.feature_auth.domain.entity.UserState>(com.khve.feature_auth.domain.entity.UserState.Initial)
    val currentUser = _currentUser.asStateFlow()

    init {
        updateUserState()
    }

    fun signOut() {
        userManager.signOut()
    }

    private fun updateUserState() {
        viewModelScope.launch {
            userManager.userState.collect {
                _currentUser.value = it
            }
        }
    }
}
