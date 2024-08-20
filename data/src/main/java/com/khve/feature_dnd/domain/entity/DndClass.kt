package com.khve.feature_dnd.domain.entity

data class DndClass(
    val name: String,
    val previewImage: String,
    val perks: List<DndItem>,
    val skills: List<DndItem>
)
