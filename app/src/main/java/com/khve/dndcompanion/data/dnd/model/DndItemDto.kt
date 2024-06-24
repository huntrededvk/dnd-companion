package com.khve.dndcompanion.data.dnd.model

import com.google.gson.annotations.SerializedName

data class DndItemDto(
    @SerializedName("name") val name: String = DEFAULT_EMPTY_STRING,
    @SerializedName("preview_image") val previewImage: String = DEFAULT_EMPTY_STRING
) {
    companion object {
        private const val DEFAULT_EMPTY_STRING = ""
    }
}
