package com.khve.feature_meta.domain.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class PartySizeEnum: Parcelable {
    SOLO, DUO, TRIO
}