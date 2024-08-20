package com.khve.feature_dnd.domain.entity

data class DndContent(
    val classes: List<DndClass>,
    val weapon: List<DndItem>,
    val pendants: List<DndItem>,
    val rings: List<DndItem>,
    val armor: DndArmor
)
