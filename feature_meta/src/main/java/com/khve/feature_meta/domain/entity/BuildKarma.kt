package com.khve.feature_meta.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BuildKarma(
    val userId: String
): Parcelable