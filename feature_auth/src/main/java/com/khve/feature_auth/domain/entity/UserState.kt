package com.khve.feature_auth.domain.entity

sealed class UserState {

    data object Initial : UserState()
    data class User(val user: com.khve.feature_auth.domain.entity.User) : UserState()
    data class Error(val errorMessage: String) : UserState()
    data object NotAuthorized : UserState()
}
