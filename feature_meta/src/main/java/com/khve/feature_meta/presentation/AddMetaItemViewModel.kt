package com.khve.feature_meta.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khve.feature_auth.data.network.firebase.FirebaseUserManager
import com.khve.feature_dnd.domain.entity.DndContentState
import com.khve.feature_dnd.domain.usecase.GetDndContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddMetaItemViewModel @Inject constructor(
    private val addMetaItemUseCase: com.khve.feature_meta.domain.usecase.AddMetaItemUseCase,
    private val userManager: FirebaseUserManager,
    private val getDndContentUseCase: GetDndContentUseCase
) : ViewModel() {

    private val _metaItem = MutableStateFlow<com.khve.feature_meta.domain.entity.MetaItemState>(com.khve.feature_meta.domain.entity.MetaItemState.Initial)
    val metaItem = _metaItem.asStateFlow()

    private val _userState = MutableStateFlow<com.khve.feature_auth.domain.entity.UserState>(com.khve.feature_auth.domain.entity.UserState.Initial)

    private val _dndContentState = MutableStateFlow<DndContentState>(DndContentState.Initial)
    val dndContentState = _dndContentState.asStateFlow()


    init {
        getUserState()
        getContentData()
    }

    private fun getUserState() {
        viewModelScope.launch {
            userManager.userState.collect {
                _userState.value = it
            }
        }
    }

    private fun getContentData() {
        viewModelScope.launch {
            _dndContentState.value = DndContentState.Progress
            getDndContentUseCase().collect {
                _dndContentState.value = it
            }
        }
    }

    fun addMetaItem(metaItem: com.khve.feature_meta.domain.entity.MetaItem) {
        viewModelScope.launch {
            _metaItem.value = com.khve.feature_meta.domain.entity.MetaItemState.Progress
            when {
                metaItem.tier.isEmpty() -> "Tier cannot be empty"
                metaItem.description.isEmpty() -> "Description cannot be empty"
                metaItem.dndClass.isEmpty() -> "Class cannot be empty"
                else -> null
            }?.let {
                _metaItem.value = com.khve.feature_meta.domain.entity.MetaItemState.Error(it)
                return@launch
            }

            val currentUser = (_userState.value as? com.khve.feature_auth.domain.entity.UserState.User)?.user
            val currentContent = (_dndContentState.value as? DndContentState.Content)?.content

            if (currentUser?.hasPermission(com.khve.feature_auth.domain.entity.Permission.ADD_META_ITEM) == true && currentContent != null) {
                val metaItemToSave = metaItem.copy(
                    author = mapOf(
                        com.khve.feature_meta.domain.entity.MetaItem.USERNAME to currentUser.username,
                        com.khve.feature_meta.domain.entity.MetaItem.USER_UID to currentUser.uid
                    ),
                )
                addMetaItemUseCase(metaItemToSave).collect {
                    _metaItem.value = it
                }
            } else {
                _metaItem.value = com.khve.feature_meta.domain.entity.MetaItemState.Error("No permission to add item")
            }
        }
    }
}