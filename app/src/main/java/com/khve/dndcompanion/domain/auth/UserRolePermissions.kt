package com.khve.dndcompanion.domain.auth

import com.khve.dndcompanion.domain.auth.entity.User
import com.khve.dndcompanion.domain.auth.enum.Permission
import com.khve.dndcompanion.domain.auth.enum.UserRole

class UserRolePermissions {
        companion object {
            private val permissionsMap = mapOf(
                UserRole.ADMIN to Permission.values().toList(),
                UserRole.CONTENT_CREATOR to listOf(Permission.ADD_META_ITEM),
                UserRole.STREAMER to listOf(Permission.ADD_META_ITEM),
                UserRole.MODERATOR to listOf(Permission.ADD_META_ITEM)
            )

            fun hasPermission(user: User, permission: Permission): Boolean {
                return user.role.flatMap { permissionsMap[it] ?: emptyList() }.contains(permission)
            }
        }
}