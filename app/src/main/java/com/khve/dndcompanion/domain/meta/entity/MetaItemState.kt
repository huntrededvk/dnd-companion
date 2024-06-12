package com.khve.dndcompanion.domain.meta.entity

sealed class MetaItemState {
    data object Initial: MetaItemState()
    data object Progress: MetaItemState()
    data object Success: MetaItemState()
    class MetaItem(metaItem: com.khve.dndcompanion.domain.meta.entity.MetaItem): MetaItemState()
    data class Error(val errorMessage: String): MetaItemState()
}