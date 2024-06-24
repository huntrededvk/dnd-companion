package com.khve.dndcompanion.data.meta.model

import com.google.gson.annotations.SerializedName

data class MetaCardItemDto(
    @SerializedName("uid") val uid: String = DEFAULT_EMPTY_STRING,
    @SerializedName("title") val title: String = DEFAULT_EMPTY_STRING,
    @SerializedName("tier") val tier: String = DEFAULT_EMPTY_STRING,
    @SerializedName("author") val author: Map<String, String> = mapOf(
        USERNAME to DEFAULT_EMPTY_STRING,
        USER_UID to DEFAULT_EMPTY_STRING
    ),
    @SerializedName("dnd_class") val dndClass: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    )
) {
    companion object {
        private const val DEFAULT_EMPTY_STRING = ""
        const val NAME = "name"
        const val USER_UID = "uid"
        const val USERNAME = "username"
        const val PREVIEW_IMAGE = "preview_image"
    }
}