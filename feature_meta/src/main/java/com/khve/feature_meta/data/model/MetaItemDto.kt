package com.khve.feature_meta.data.model

import com.google.gson.annotations.SerializedName
import com.khve.feature_meta.domain.entity.PartySizeEnum

data class MetaItemDto(
    @SerializedName("uid") val uid: String = DEFAULT_EMPTY_STRING,
    @SerializedName("title") val title: String = DEFAULT_EMPTY_STRING,
    @SerializedName("likes") val likes: List<BuildKarmaDto> = emptyList(),
    @SerializedName("dislikes") val dislikes: List<BuildKarmaDto> = emptyList(),
    @SerializedName("party_size") val partySize: PartySizeEnum? = null,
    @SerializedName("description") val description: String = DEFAULT_EMPTY_STRING,
    @SerializedName("tier") val tier: String = DEFAULT_EMPTY_STRING,
    @SerializedName("youtube_video_id") val youtubeVideoId: String = DEFAULT_EMPTY_STRING,
    @SerializedName("activated") val activated: Boolean = false,
    @SerializedName("author") val author: Map<String, String> = mapOf(
        USERNAME to DEFAULT_EMPTY_STRING,
        USER_UID to DEFAULT_EMPTY_STRING
    ),
    @SerializedName("dnd_class") val dndClass: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    @SerializedName("primary_weapon_slot_one") val primaryWeaponSlotOne: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    @SerializedName("primary_weapon_slot_two") val primaryWeaponSlotTwo: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    @SerializedName("secondary_weapon_slot_one") val secondaryWeaponSlotOne: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    @SerializedName("secondary_weapon_slot_two") val secondaryWeaponSlotTwo: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    @SerializedName("head_armor") val headArmor: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    @SerializedName("chest_armor") val chestArmor: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    @SerializedName("legs_armor") val legsArmor: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    @SerializedName("foot_armor") val footArmor: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    @SerializedName("gloves_armor") val glovesArmor: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    @SerializedName("back_armor") val backArmor: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    @SerializedName("pendant") val pendant: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    @SerializedName("ring_slot_one") val ringSlotOne: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    @SerializedName("ring_slot_two") val ringSlotTwo: Map<String, String> = mapOf(
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
