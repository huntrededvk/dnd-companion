package com.khve.dndcompanion.domain.meta.entity

sealed class MetaCardListState {

    data object Initial : MetaCardListState()
    data object Progress: MetaCardListState()
    data class MetaCardList(val metaCardList: List<MetaCardItem>) : MetaCardListState()
    data class Error(val errorMessage: String) : MetaCardListState()
}
