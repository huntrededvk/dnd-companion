package com.khve.feature_auth.domain

import com.khve.feature_auth.domain.entity.Permission
import com.khve.feature_auth.domain.entity.User
import com.khve.feature_auth.domain.entity.UserRole

class UserRolePermissions {
    companion object {
        private val permissionsMap = mapOf(
            UserRole.ADMIN to Permission.entries,
            UserRole.MODERATOR to listOf(
                Permission.ADD_META_ITEM,
                Permission.DELETE_META_ITEM,
                Permission.APPROVE_SELF_META_ITEM
            ),
            UserRole.CONTENT_CREATOR to listOf(
                Permission.ADD_META_ITEM,
                Permission.APPROVE_SELF_META_ITEM
            ),
            UserRole.STREAMER to listOf(
                Permission.ADD_META_ITEM,
                Permission.APPROVE_SELF_META_ITEM
            ),
            UserRole.USER to listOf(Permission.ADD_META_ITEM),
            UserRole.AUTHORIZED to listOf(Permission.ADD_META_ITEM)
        )

        fun hasPermission(user: User, permission: Permission): Boolean {
            return user.role.flatMap { permissionsMap[it] ?: emptyList() }.contains(permission)
        }
    }
}
