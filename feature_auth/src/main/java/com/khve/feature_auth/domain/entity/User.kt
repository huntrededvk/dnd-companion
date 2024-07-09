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
    val role: List<UserRole> = listOf()
): Parcelable {

    fun hasPermission(permission: Permission): Boolean {
        return UserRolePermissions.hasPermission(this, permission)
    }

    fun hasOneOfRoles(userRoles: List<UserRole>, rolesToCompare: List<UserRole>): Boolean {
        for (role in rolesToCompare) {
            if (userRoles.contains(role))
                return true
        }

        return false
    }

    companion object {
        private const val EMPTY_STRING_VALUE = ""
    }
}
