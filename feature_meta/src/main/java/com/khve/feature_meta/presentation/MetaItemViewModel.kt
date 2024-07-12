package com.khve.feature_meta.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khve.feature_auth.data.network.firebase.FirebaseUserManager
import com.khve.feature_meta.domain.usecase.DeleteMetaItemUseCase
import com.khve.feature_meta.domain.usecase.GetMetaItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MetaItemViewModel @Inject constructor(
    private val deleteMetaItemUseCase: DeleteMetaItemUseCase,
    private val getMetaItemUseCase: GetMetaItemUseCase,
    private val userManager: FirebaseUserManager
) : ViewModel() {

    private val _metaItemState = MutableStateFlow<com.khve.feature_meta.domain.entity.MetaItemState>(
        com.khve.feature_meta.domain.entity.MetaItemState.Initial)
    val metaItemState = _metaItemState.asStateFlow()
    
    fun compareUserUidWith(uid: String): Boolean {
        return userManager.compareUserUidWith(uid)
    }
    
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
