package com.khve.feature_dnd.domain.entity

sealed class DndClassState {
    data object Initial : DndClassState()
    class ClassList(val classes: List<DndClass>) : DndClassState()
}
