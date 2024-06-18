package com.khve.dndcompanion.domain.dnd.entity

import com.google.gson.annotations.SerializedName

data class DndArmor (
    @SerializedName("head") val head: List<DndItem>,
    @SerializedName("chest") val chest: List<DndItem>,
    @SerializedName("legs") val legs: List<DndItem>,
    @SerializedName("foot") val foot: List<DndItem>,
    @SerializedName("back") val back: List<DndItem>,
    @SerializedName("gloves") val gloves: List<DndItem>
)