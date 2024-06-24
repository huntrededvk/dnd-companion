package com.khve.dndcompanion.presentation.meta

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khve.dndcompanion.data.network.firebase.auth.FirebaseUserManager
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.meta.entity.MetaCardListState
import com.khve.dndcompanion.domain.meta.usecase.GetMetaCardListUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MetaListViewModel @Inject constructor(
    private val userManager: FirebaseUserManager,
    private val getMetaCardListUseCase: GetMetaCardListUseCase
) : ViewModel() {

    private val _metaCardListState = MutableStateFlow<MetaCardListState>(
        MetaCardListState.Initial
    )
    val metaCardListState = _metaCardListState.asStateFlow()

    private val _currentUser = MutableStateFlow<UserState>(UserState.Initial)
    val currentUser = _currentUser.asStateFlow()

    init {
        updateUserState()
        getMetaCardList()
    }

    private fun updateUserState() {
        viewModelScope.launch {
            userManager.userState.collect {
                _currentUser.value = it
            }
        }
    }

    fun getMetaCardList() {
        viewModelScope.launch {
            _metaCardListState.value = MetaCardListState.Progress
            getMetaCardListUseCase().collect {
                _metaCardListState.value = it
            }
        }
    }

}