package com.khve.feature_dnd.data.model

sealed class DndContentStateDto {
    data object Initial : DndContentStateDto()
    class ContentDto(val content: DndContentDto) : DndContentStateDto()
}
