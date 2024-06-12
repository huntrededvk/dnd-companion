package com.khve.dndcompanion.domain.meta.entity


data class MetaItem (
    val uid: String = EMPTY_STRING_VALUE,
    val title: String = EMPTY_STRING_VALUE,
    val description: String = EMPTY_STRING_VALUE,
    val tier: String = EMPTY_STRING_VALUE,
    val authorUsername: String = EMPTY_STRING_VALUE,
    val authorUid: String = EMPTY_STRING_VALUE,
    val previewImg: String = EMPTY_STRING_VALUE
) {
    companion object {
        private const val EMPTY_STRING_VALUE = ""
    }
}