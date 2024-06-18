package com.khve.dndcompanion.data.meta.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class MetaItemDto (
    @SerializedName("uid") val uid: String = DEFAULT_EMPTY_STRING,
    @SerializedName("title") val title: String = DEFAULT_EMPTY_STRING,
    @SerializedName("description") val description: String = DEFAULT_EMPTY_STRING,
    @SerializedName("tier") val tier: String = DEFAULT_EMPTY_STRING,
    @SerializedName("authorUsername") val authorUsername: String = DEFAULT_EMPTY_STRING,
    @SerializedName("author_uid") val authorUid: String = DEFAULT_EMPTY_STRING,
    @SerializedName("preview_img") val previewImg: String = DEFAULT_EMPTY_STRING
) : Parcelable {
    companion object {
        private const val DEFAULT_EMPTY_STRING = ""
    }
}