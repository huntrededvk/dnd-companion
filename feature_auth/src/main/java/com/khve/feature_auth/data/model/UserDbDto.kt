package com.khve.feature_auth.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.khve.feature_auth.domain.entity.UserRole
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDbDto(
    @SerializedName("uid") val uid: String = DEFAULT_EMPTY_STRING,
    @SerializedName("email") val email: String = DEFAULT_EMPTY_STRING,
    @SerializedName("username") val username: String = DEFAULT_EMPTY_STRING,
    @SerializedName("discord") val discord: String = DEFAULT_EMPTY_STRING,
    @SerializedName("role") val role: UserRole = UserRole.NOT_VERIFIED,
) : Parcelable {
    companion object {
        private const val DEFAULT_EMPTY_STRING = ""
    }
}
