package com.khve.feature_dnd.data.model

import com.google.gson.annotations.SerializedName

data class DndContentDto(
    @SerializedName("classes") val classes: List<DndClassDto>,
    @SerializedName("weapon") val weapon: List<DndItemDto>,
    @SerializedName("pendants") val pendants: List<DndItemDto>,
    @SerializedName("rings") val rings: List<DndItemDto>,
    @SerializedName("armor") val armor: DndArmorDto
)
