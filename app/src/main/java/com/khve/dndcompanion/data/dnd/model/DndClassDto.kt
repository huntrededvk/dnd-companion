package com.khve.dndcompanion.data.dnd.model

import com.google.gson.annotations.SerializedName

data class DndClassDto(
    @SerializedName("name") val name: String,
    @SerializedName("preview_image") val previewImage: String,
    @SerializedName("perks") val perks: List<DndItemDto>,
    @SerializedName("skills") val skills: List<DndItemDto>
)
