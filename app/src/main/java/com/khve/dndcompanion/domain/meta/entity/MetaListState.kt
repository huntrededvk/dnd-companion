package com.khve.dndcompanion.domain.meta.entity

sealed class MetaListState {

    data object Initial: MetaListState()
    data class MetaList(val metaList: List<MetaItem>): MetaListState()
    data class Error(val errorMessage: String): MetaListState()
}