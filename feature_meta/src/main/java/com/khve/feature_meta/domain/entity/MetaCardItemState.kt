package com.khve.feature_meta.domain.entity

sealed class MetaCardItemState {
    data object Initial : MetaCardItemState()
    data object Progress : MetaCardItemState()
    data object Success : MetaCardItemState()
    class MetaCardItem(val metaCardItem: com.khve.feature_meta.domain.entity.MetaCardItem) : MetaCardItemState()
    data class Error(val errorMessage: String) : MetaCardItemState()
}
