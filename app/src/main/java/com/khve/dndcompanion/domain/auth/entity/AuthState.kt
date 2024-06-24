package com.khve.dndcompanion.domain.auth.entity

sealed class AuthState {

    data object Initial : AuthState()
    data object Progress : AuthState()
    class Error(val error: String) : AuthState()
}
