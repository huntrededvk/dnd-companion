package com.khve.dndcompanion.domain.meta.entity

import com.khve.dndcompanion.domain.auth.entity.User


data class MetaItem (
    val id: Int,
    val title: String,
    val author: User,
    val previewImg: String,
    val createdAt: Long,
    val deletedAt: Long
)