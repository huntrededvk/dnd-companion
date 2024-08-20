package com.khve.feature_auth.domain

import com.khve.feature_auth.domain.entity.Permission
import com.khve.feature_auth.domain.entity.User
import com.khve.feature_auth.domain.entity.UserRole

object UserRolePermissions {
    private val moderator = listOf(
        Permission.DELETE_META_ITEM
    )
    private val streamer = emptyList<Permission>()
    private val contentCreator = listOf(
        Permission.APPROVE_SELF_META_ITEM
    )
    private val user = listOf(
        Permission.ADD_META_ITEM,
        Permission.LIKE_DISLIKE_BUILD
    )

    // TODO: Change authorized permissions to none
    private val authorized = listOf(
        Permission.ADD_META_ITEM,
        Permission.LIKE_DISLIKE_BUILD
    )

    private val permissionsMap = mapOf(
        UserRole.ADMIN to Permission.entries,
        UserRole.MODERATOR to moderator + user + streamer + contentCreator,
        UserRole.CONTENT_CREATOR to contentCreator + user,
        UserRole.STREAMER to streamer + user + contentCreator,
        UserRole.USER to user,
        UserRole.AUTHORIZED to authorized
    )

    fun hasPermission(user: User, permission: Permission): Boolean {
        return user.role.flatMap { permissionsMap[it] ?: emptyList() }.contains(permission)
    }
}
