package com.khve.feature_auth.domain.entity

enum class UserRole(val title: String) {
    ADMIN("Admin"),
    MODERATOR("Moderator"),
    CONTENT_CREATOR("Content Creator"),
    STREAMER("Streamer"),
    USER("Dungeon seeker"),
    NOT_VERIFIED("Not Verified"),
    BANNED("Banned")
}
