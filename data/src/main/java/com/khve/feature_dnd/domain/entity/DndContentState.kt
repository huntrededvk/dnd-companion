package com.khve.feature_dnd.domain.entity

sealed class DndContentState {
    data object Initial : DndContentState()
    data object Progress : DndContentState()
    class Content(val content: DndContent) : DndContentState()
}
