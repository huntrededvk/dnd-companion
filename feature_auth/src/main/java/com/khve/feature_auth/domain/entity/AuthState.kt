package com.khve.feature_auth.domain.entity

sealed class AuthState {

    data object Initial : AuthState()
    data object Progress : AuthState()
    class Error(val error: String) : AuthState()
}
