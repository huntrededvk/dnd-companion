package com.khve.dndcompanion.domain.dnd.entity

sealed class DndContentState {
    data object Initial : DndContentState()
    class Content(val content: DndContent): DndContentState()
}