package com.khve.dndcompanion.data.dnd.model

import com.google.gson.annotations.SerializedName

data class DnDItemDto (
    @SerializedName("item_name") val itemName: String,
    @SerializedName("preview_image") val previewImage: String
)