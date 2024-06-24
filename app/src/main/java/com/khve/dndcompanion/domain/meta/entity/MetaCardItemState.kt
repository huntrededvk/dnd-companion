package com.khve.dndcompanion.domain.meta.entity

sealed class MetaCardItemState {
    data object Initial : MetaCardItemState()
    data object Progress : MetaCardItemState()
    data object Success : MetaCardItemState()
    class MetaCardItem(val metaCardItem: com.khve.dndcompanion.domain.meta.entity.MetaCardItem) : MetaCardItemState()
    data class Error(val errorMessage: String) : MetaCardItemState()
}
