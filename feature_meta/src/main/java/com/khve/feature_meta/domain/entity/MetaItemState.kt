package com.khve.feature_meta.domain.entity

sealed class MetaItemState {
    data object Initial : MetaItemState()
    data object Progress : MetaItemState()
    data object Success : MetaItemState()
    class MetaItem(val metaItem: com.khve.feature_meta.domain.entity.MetaItem) : MetaItemState()
    data class Error(
        val errorMessage: String,
        val notAuthorized: Boolean? = null
    ) : MetaItemState()
}
