package com.khve.dndcompanion.domain.meta.entity

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class PartySizeEnum: Parcelable {
    SOLO, DUO, TRIO
}