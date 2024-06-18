package com.khve.dndcompanion.domain.dnd.entity

import com.google.gson.annotations.SerializedName

data class DndContent (
    @SerializedName("classes") val classes: List<DndClass>,
    @SerializedName("weapon") val weapon: List<DndItem>,
    @SerializedName("pendants") val pendants: List<DndItem>,
    @SerializedName("rings") val rings: List<DndItem>,
    @SerializedName("armor") val armor: DndArmor
)