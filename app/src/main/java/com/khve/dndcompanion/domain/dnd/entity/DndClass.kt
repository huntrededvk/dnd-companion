package com.khve.dndcompanion.domain.dnd.entity

import com.google.gson.annotations.SerializedName

data class DndClass (
    @SerializedName("name") val name: String,
    @SerializedName("preview_image") val previewImage: String,
    @SerializedName("perks") val perks: List<DndItem>,
    @SerializedName("skills") val skills: List<DndItem>
)