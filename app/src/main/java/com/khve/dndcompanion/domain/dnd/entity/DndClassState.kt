package com.khve.dndcompanion.domain.dnd.entity

sealed class DndClassState {
    data object Initial : DndClassState()
    class ClassList(val classes: List<DndClass>): DndClassState()
}