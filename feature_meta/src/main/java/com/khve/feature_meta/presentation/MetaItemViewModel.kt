package com.khve.feature_meta.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MetaItemViewModel @Inject constructor(
    private val deleteMetaItemUseCase: com.khve.feature_meta.domain.usecase.DeleteMetaItemUseCase,
    private val getMetaItemUseCase: com.khve.feature_meta.domain.usecase.GetMetaItemUseCase
) : ViewModel() {

    private val _metaItemState = MutableStateFlow<com.khve.feature_meta.domain.entity.MetaItemState>(
        com.khve.feature_meta.domain.entity.MetaItemState.Initial)
    val metaItemState = _metaItemState.asStateFlow()

    fun getMetaItem(metaItemUid: String, partySize: com.khve.feature_meta.domain.entity.PartySizeEnum) {
        viewModelScope.launch {
            _metaItemState.value = com.khve.feature_meta.domain.entity.MetaItemState.Progress
            getMetaItemUseCase(metaItemUid, partySize).collect {
                _metaItemState.value = it
            }
        }
    }

    fun deleteMetaItem(metaItem: com.khve.feature_meta.domain.entity.MetaItem) {
        viewModelScope.launch {
            _metaItemState.value = com.khve.feature_meta.domain.entity.MetaItemState.Progress
            deleteMetaItemUseCase(metaItem).collect {
                _metaItemState.value = it
            }
        }
    }
}
