package com.khve.dndcompanion.domain.auth.entity

import com.khve.dndcompanion.domain.auth.enum.UserRole

data class User(
    val id: Int = DEFAULT_ID,
    val uid: String,
    val email: String,
    val username: String,
    val discord: String,
    val role: UserRole
) {
    companion object {
        private const val DEFAULT_ID = -1
    }
}