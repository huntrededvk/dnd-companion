package com.khve.feature_dnd.domain.entity

data class DndItem(
    val name: String = DEFAULT_EMPTY_STRING,
    val previewImage: String = DEFAULT_EMPTY_STRING
) {
    companion object {
        private const val DEFAULT_EMPTY_STRING = ""
    }
}
