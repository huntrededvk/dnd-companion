package com.khve.dndcompanion.data.auth.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.khve.dndcompanion.domain.auth.enum.UserRole
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDbDto(
    @SerializedName("uid") val uid: String = DEFAULT_EMPTY_STRING,
    @SerializedName("email") val email: String = DEFAULT_EMPTY_STRING,
    @SerializedName("username") val username: String = DEFAULT_EMPTY_STRING,
    @SerializedName("discord") val discord: String = DEFAULT_EMPTY_STRING,
    @SerializedName("role") val role: List<UserRole> = listOf(UserRole.NOT_AUTHORIZED),
) : Parcelable {
    companion object {
        private const val DEFAULT_EMPTY_STRING = ""
    }
}
