package com.khve.feature_meta.data.model

import com.google.gson.annotations.SerializedName

data class BuildKarmaDto(
    @SerializedName("user_id") val userId: String = USER_ID_DEFAULT
) {
    companion object {
        const val USER_ID_DEFAULT = ""
    }
}