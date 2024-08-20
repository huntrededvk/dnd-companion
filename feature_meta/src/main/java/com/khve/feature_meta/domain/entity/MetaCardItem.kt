package com.khve.feature_meta.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MetaCardItem (
    val uid: String = DEFAULT_EMPTY_STRING,
    val title: String = DEFAULT_EMPTY_STRING,
    val likes: List<BuildKarma> = emptyList(),
    val dislikes: List<BuildKarma> = emptyList(),
    val partySize: PartySizeEnum?,
    val tier: String = DEFAULT_EMPTY_STRING,
    val activated: Boolean = false,
    val author: Map<String, String> = mapOf(
        USERNAME to DEFAULT_EMPTY_STRING,
        USER_UID to DEFAULT_EMPTY_STRING
    ),
    val dndClass: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    )
): Parcelable {
    companion object {
        private const val DEFAULT_EMPTY_STRING = ""
        const val NAME = "name"
        const val USER_UID = "uid"
        const val USERNAME = "username"
        const val PREVIEW_IMAGE = "preview_image"
    }
}