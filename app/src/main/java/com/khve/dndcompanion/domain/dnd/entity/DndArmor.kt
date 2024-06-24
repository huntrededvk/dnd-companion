package com.khve.dndcompanion.domain.dnd.entity

data class DndArmor(
    val head: List<DndItem>,
    val chest: List<DndItem>,
    val legs: List<DndItem>,
    val foot: List<DndItem>,
    val back: List<DndItem>,
    val gloves: List<DndItem>
)
