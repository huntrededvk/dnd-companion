package com.khve.feature_dnd.data.model

import com.google.gson.annotations.SerializedName

data class DndArmorDto(
    @SerializedName("head") val head: List<DndItemDto>,
    @SerializedName("chest") val chest: List<DndItemDto>,
    @SerializedName("legs") val legs: List<DndItemDto>,
    @SerializedName("foot") val foot: List<DndItemDto>,
    @SerializedName("back") val back: List<DndItemDto>,
    @SerializedName("gloves") val gloves: List<DndItemDto>
)
