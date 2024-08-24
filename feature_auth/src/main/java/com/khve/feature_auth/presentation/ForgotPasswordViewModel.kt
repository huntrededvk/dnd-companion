package com.khve.feature_auth.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khve.feature_auth.data.network.firebase.FirebaseUserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val userManager: FirebaseUserManager
) : ViewModel() {

    private val _notification = MutableStateFlow<String?>(null)
    val notification = _notification.asStateFlow()

    fun resetPassword(email: String) {
        viewModelScope.launch {
            userManager.forgotPassword(email).collect {
                _notification.value = it
            }
        }
    }

}