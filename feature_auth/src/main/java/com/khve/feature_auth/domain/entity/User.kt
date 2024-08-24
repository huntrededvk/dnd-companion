package com.khve.feature_auth.domain.entity

import android.os.Parcelable
import com.khve.feature_auth.domain.UserRolePermissions
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val uid: String = EMPTY_STRING_VALUE,
    val email: String = EMPTY_STRING_VALUE,
    val username: String = EMPTY_STRING_VALUE,
    val discord: String = EMPTY_STRING_VALUE,
    val role: UserRole = UserRole.NOT_VERIFIED
): Parcelable {

    companion object {
        private const val EMPTY_STRING_VALUE = ""
    }
}
