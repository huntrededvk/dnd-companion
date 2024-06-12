package com.khve.dndcompanion.presentation.meta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khve.dndcompanion.data.network.FirebaseUserManager
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.meta.entity.MetaListState
import com.khve.dndcompanion.domain.meta.usecase.GetMetaListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class MetaListViewModel @Inject constructor(
    private val userManager: FirebaseUserManager,
    private val getMetaListUseCase: GetMetaListUseCase
) : ViewModel() {

    private val _metaList = MutableStateFlow<MetaListState>(MetaListState.Initial)
    val metaList = _metaList.asStateFlow()

    private val _currentUser = MutableStateFlow<UserState>(UserState.Initial)
    val currentUser = _currentUser.asStateFlow()

    init {
        updateUserState()
        getMetaList()
    }

    private fun updateUserState() {
        viewModelScope.launch {
            userManager.userState.collect {
                _currentUser.value = it
            }
        }
    }

    fun getMetaList() {
        viewModelScope.launch {
            getMetaListUseCase().collect {
                _metaList.value = it
            }
        }
    }

}