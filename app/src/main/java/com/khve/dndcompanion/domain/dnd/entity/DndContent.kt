package com.khve.dndcompanion.domain.dnd.entity

data class DndContent(
    val classes: List<DndClass>,
    val weapon: List<DndItem>,
    val pendants: List<DndItem>,
    val rings: List<DndItem>,
    val armor: DndArmor
)
