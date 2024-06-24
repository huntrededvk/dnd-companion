package com.khve.dndcompanion.domain.meta.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MetaItem(
    val uid: String = DEFAULT_EMPTY_STRING,
    val title: String = DEFAULT_EMPTY_STRING,
    val description: String = DEFAULT_EMPTY_STRING,
    val tier: String = DEFAULT_EMPTY_STRING,
    val author: Map<String, String> = mapOf(
        USERNAME to DEFAULT_EMPTY_STRING,
        USER_UID to DEFAULT_EMPTY_STRING
    ),
    val dndClass: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    val primaryWeaponSlotOne: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    val primaryWeaponSlotTwo: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    val secondaryWeaponSlotOne: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    val secondaryWeaponSlotTwo: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    val headArmor: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    val chestArmor: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    val legsArmor: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    val footArmor: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    val glovesArmor: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    val backArmor: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    val pendant: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    val ringSlotOne: Map<String, String> = mapOf(
        NAME to DEFAULT_EMPTY_STRING,
        PREVIEW_IMAGE to DEFAULT_EMPTY_STRING
    ),
    val ringSlotTwo: Map<String, String> = mapOf(
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
