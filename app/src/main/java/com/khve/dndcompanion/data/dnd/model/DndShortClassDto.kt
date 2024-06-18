package com.khve.dndcompanion.data.dnd.model

import com.google.gson.annotations.SerializedName

data class DndShortClassDto(
    @SerializedName("name") val name: String,
    @SerializedName("previewImage") val previewImage: String
)