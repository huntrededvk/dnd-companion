package com.khve.feature_dnd.data.model

sealed class DndClassStateDto {
    data object Initial : DndClassStateDto()
    class ClassListDto(val classes: List<DndClassDto>) : DndClassStateDto()
}
