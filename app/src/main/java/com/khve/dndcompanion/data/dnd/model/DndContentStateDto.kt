package com.khve.dndcompanion.data.dnd.model

sealed class DndContentStateDto {
    data object Initial : DndContentStateDto()
    class ContentDto(val content: DndContentDto) : DndContentStateDto()
}
