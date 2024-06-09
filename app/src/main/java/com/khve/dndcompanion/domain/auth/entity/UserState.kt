package com.khve.dndcompanion.domain.auth.entity

import com.google.firebase.auth.FirebaseUser

sealed class UserState {

    object Initial: UserState()
    object Progress: UserState()
    data class Authorized(val user: FirebaseUser): UserState()
    data class Error(val errorMessage: String): UserState()
    object NotAuthorized: UserState()
}