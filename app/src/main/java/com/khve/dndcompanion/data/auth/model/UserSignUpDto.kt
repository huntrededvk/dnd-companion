package com.khve.dndcompanion.data.auth.model

import com.google.gson.annotations.SerializedName

data class UserSignUpDto (
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String,
    @SerializedName("username") val username: String,
    @SerializedName("discord") val discord: String,
)