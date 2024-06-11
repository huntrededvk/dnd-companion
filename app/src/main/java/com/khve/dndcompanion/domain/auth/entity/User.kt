package com.khve.dndcompanion.domain.auth.entity

import android.os.Parcelable
import com.khve.dndcompanion.domain.auth.enum.UserRole
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uid: String = EMPTY_STRING_VALUE,
    val email: String = EMPTY_STRING_VALUE,
    val username: String = EMPTY_STRING_VALUE,
    val discord: String = EMPTY_STRING_VALUE,
    val role: List<UserRole> = listOf()
) : Parcelable {
    companion object {
        private const val EMPTY_STRING_VALUE = ""
    }
}