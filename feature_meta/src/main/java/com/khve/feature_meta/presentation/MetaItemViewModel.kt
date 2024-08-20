package com.khve.feature_meta.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khve.feature_auth.data.network.firebase.FirebaseUserManager
import com.khve.feature_meta.domain.entity.MetaItem
import com.khve.feature_meta.domain.entity.MetaItemState
import com.khve.feature_meta.domain.entity.PartySizeEnum
import com.khve.feature_meta.domain.usecase.DeleteMetaItemUseCase
import com.khve.feature_meta.domain.usecase.DislikeMetaItemUseCase
import com.khve.feature_meta.domain.usecase.GetMetaItemUseCase
import com.khve.feature_meta.domain.usecase.LikeMetaItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MetaItemViewModel @Inject constructor(
    private val deleteMetaItemUseCase: DeleteMetaItemUseCase,
    private val getMetaItemUseCase: GetMetaItemUseCase,
    private val likeMetaItemUseCase: LikeMetaItemUseCase,
    private val dislikeMetaItemUseCase: DislikeMetaItemUseCase,
    private val userManager: FirebaseUserManager
) : ViewModel() {

    private val _metaItemState = MutableStateFlow<MetaItemState>(
        MetaItemState.Initial)
    val metaItemState = _metaItemState.asStateFlow()
    
    fun compareUserUidWith(uid: String): Boolean {
        return userManager.compareUserUidWith(uid)
    }

    fun like(metaItem: MetaItem) {
        likeMetaItemUseCase.invoke(metaItem)
    }

    fun dislike(metaItem: MetaItem) {
        dislikeMetaItemUseCase.invoke(metaItem)
    }
    
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
