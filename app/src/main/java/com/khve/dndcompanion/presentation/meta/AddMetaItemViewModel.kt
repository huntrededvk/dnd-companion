package com.khve.dndcompanion.presentation.meta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khve.dndcompanion.data.meta.model.MetaItemDto
import com.khve.dndcompanion.data.network.FirebaseUserManager
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.auth.enum.Permission
import com.khve.dndcompanion.domain.meta.entity.MetaItemState
import com.khve.dndcompanion.domain.meta.usecase.AddMetaItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddMetaItemViewModel @Inject constructor(
    private val addMetaItemUseCase: AddMetaItemUseCase,
    private val userManager: FirebaseUserManager
): ViewModel() {

    private val _metaItem = MutableStateFlow<MetaItemState>(MetaItemState.Initial)
    val metaItem = _metaItem.asStateFlow()

    private val _userState = MutableStateFlow<UserState>(UserState.Initial)

    init {
        viewModelScope.launch {
            userManager.userState.collect {
                _userState.value = it
            }
        }
    }

    fun addMetaItem(metaItemDto: MetaItemDto) {
        // TODO: Make it clean code
        viewModelScope.launch {
            _metaItem.value = MetaItemState.Progress
            if (metaItemDto.tier.isEmpty()) {
                _metaItem.value = MetaItemState.Error("Tier can not be empty")
            }
            val currentUserState = _userState.value
            if (currentUserState is UserState.User &&
                currentUserState.user.hasPermission(Permission.ADD_META_ITEM)) {
                val metaItemDtoToSave = metaItemDto.copy(
                    authorUid = currentUserState.user.uid,
                    authorUsername = currentUserState.user.username
                )
                addMetaItemUseCase(metaItemDtoToSave).collect {
                    _metaItem.value = it
                }
            } else {
                _metaItem.value = MetaItemState.Error("No permission to add item")
            }
        }
    }

}