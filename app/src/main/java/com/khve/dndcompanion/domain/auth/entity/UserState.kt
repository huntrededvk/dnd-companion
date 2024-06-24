package com.khve.dndcompanion.domain.auth.entity

sealed class UserState {

    data object Initial : UserState()
    data class User(val user: com.khve.dndcompanion.domain.auth.entity.User) : UserState()
    data class Error(val errorMessage: String) : UserState()
    data object NotAuthorized : UserState()
}
