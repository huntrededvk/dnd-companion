package com.khve.dndcompanion.data.dnd.model

sealed class DndClassStateDto {
    data object Initial : DndClassStateDto()
    class ClassListDto(val classes: List<DndClassDto>) : DndClassStateDto()
}
