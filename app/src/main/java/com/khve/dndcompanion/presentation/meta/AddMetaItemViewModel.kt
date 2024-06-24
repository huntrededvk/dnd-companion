package com.khve.dndcompanion.presentation.meta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khve.dndcompanion.data.network.firebase.auth.FirebaseUserManager
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.auth.enum.Permission
import com.khve.dndcompanion.domain.dnd.entity.DndContentState
import com.khve.dndcompanion.domain.dnd.usecase.GetDndContentUseCase
import com.khve.dndcompanion.domain.meta.entity.MetaItem
import com.khve.dndcompanion.domain.meta.entity.MetaItemState
import com.khve.dndcompanion.domain.meta.usecase.AddMetaItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddMetaItemViewModel @Inject constructor(
    private val addMetaItemUseCase: AddMetaItemUseCase,
    private val userManager: FirebaseUserManager,
    private val getDndContentUseCase: GetDndContentUseCase
) : ViewModel() {

    private val _metaItem = MutableStateFlow<MetaItemState>(MetaItemState.Initial)
    val metaItem = _metaItem.asStateFlow()

    private val _userState = MutableStateFlow<UserState>(UserState.Initial)

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

    fun addMetaItem(metaItem: MetaItem) {
        viewModelScope.launch {
            _metaItem.value = MetaItemState.Progress
            when {
                metaItem.tier.isEmpty() -> "Tier cannot be empty"
                metaItem.description.isEmpty() -> "Description cannot be empty"
                metaItem.dndClass.isEmpty() -> "Class cannot be empty"
                else -> null
            }?.let {
                _metaItem.value = MetaItemState.Error(it)
                return@launch
            }

            val currentUser = (_userState.value as? UserState.User)?.user
            val currentContent = (_dndContentState.value as? DndContentState.Content)?.content

            if (currentUser?.hasPermission(Permission.ADD_META_ITEM) == true && currentContent != null) {
                val metaItemToSave = metaItem.copy(
                    author = mapOf(
                        MetaItem.USERNAME to currentUser.username,
                        MetaItem.USER_UID to currentUser.uid
                    ),
                )
                addMetaItemUseCase(metaItemToSave).collect {
                    _metaItem.value = it
                }
            } else {
                _metaItem.value = MetaItemState.Error("No permission to add item")
            }
        }
    }
}
