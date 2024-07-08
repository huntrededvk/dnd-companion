package com.khve.dndcompanion.presentation.meta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khve.dndcompanion.domain.meta.entity.PartySizeEnum
import com.khve.dndcompanion.domain.meta.entity.MetaItem
import com.khve.dndcompanion.domain.meta.entity.MetaItemState
import com.khve.dndcompanion.domain.meta.usecase.DeleteMetaItemUseCase
import com.khve.dndcompanion.domain.meta.usecase.GetMetaItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MetaItemViewModel @Inject constructor(
    private val deleteMetaItemUseCase: DeleteMetaItemUseCase,
    private val getMetaItemUseCase: GetMetaItemUseCase
) : ViewModel() {

    private val _metaItemState = MutableStateFlow<MetaItemState>(MetaItemState.Initial)
    val metaItemState = _metaItemState.asStateFlow()

    fun getMetaItem(metaItemUid: String, partySize: PartySizeEnum) {
        viewModelScope.launch {
            _metaItemState.value = MetaItemState.Progress
            getMetaItemUseCase(metaItemUid, partySize).collect {
                _metaItemState.value = it
            }
        }
    }

    fun deleteMetaItem(metaItem: MetaItem) {
        viewModelScope.launch {
            _metaItemState.value = MetaItemState.Progress
            deleteMetaItemUseCase(metaItem).collect {
                _metaItemState.value = it
            }
        }
    }
}
