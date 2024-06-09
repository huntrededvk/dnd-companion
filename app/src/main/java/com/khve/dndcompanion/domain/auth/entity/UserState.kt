package com.khve.dndcompanion.domain.auth.entity

import com.google.firebase.auth.FirebaseUser

sealed class UserState {

    data object Initial: UserState()
    data object Progress: UserState()
    data class Authorized(val user: FirebaseUser): UserState()
    data class Error(val errorMessage: String): UserState()
    data object NotAuthorized: UserState()
}