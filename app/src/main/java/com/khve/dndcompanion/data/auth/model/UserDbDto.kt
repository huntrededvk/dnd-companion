package com.khve.dndcompanion.data.auth.model

import com.google.gson.annotations.SerializedName
import com.khve.dndcompanion.domain.auth.enum.UserRole

data class UserDbDto (
    @SerializedName("email") val email: String,
    @SerializedName("username") val username: String,
    @SerializedName("discord") val discord: String,
    @SerializedName("role") val role: List<UserRole>,
)