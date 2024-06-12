package com.khve.dndcompanion.presentation.meta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khve.dndcompanion.data.meta.model.MetaItemDto
import com.khve.dndcompanion.domain.meta.entity.MetaItemState
import com.khve.dndcompanion.domain.meta.usecase.AddMetaItemUseCase
import com.khve.dndcompanion.domain.meta.usecase.GetMetaItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MetaItemViewModel @Inject constructor(
    private val addMetaItemUseCase: AddMetaItemUseCase,
    private val getMetaItemUseCase: GetMetaItemUseCase
): ViewModel() {

    private val _metaItemState = MutableStateFlow<MetaItemState>(MetaItemState.Initial)
    val metaItemState = _metaItemState.asStateFlow()

    fun addMetaItem(metaItemDto: MetaItemDto) {
        viewModelScope.launch {
            addMetaItemUseCase(metaItemDto).collect {
                _metaItemState.value = it
            }
        }
    }

    fun getMetaItem(metaItemUid: String) {
        viewModelScope.launch {
            getMetaItemUseCase(metaItemUid).collect {
                _metaItemState.value = it
            }
        }
    }
}