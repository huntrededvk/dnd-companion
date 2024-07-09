package com.khve.feature_meta.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khve.feature_auth.data.network.firebase.FirebaseUserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MetaListViewModel @Inject constructor(
    private val userManager: FirebaseUserManager,
    private val getMetaCardListUseCase: com.khve.feature_meta.domain.usecase.GetMetaCardListUseCase
) : ViewModel() {

    private val _metaCardListState = MutableStateFlow<com.khve.feature_meta.domain.entity.MetaCardListState>(
        com.khve.feature_meta.domain.entity.MetaCardListState.Initial
    )
    val metaCardListState = _metaCardListState.asStateFlow()

    private val _currentUser = MutableStateFlow<com.khve.feature_auth.domain.entity.UserState>(com.khve.feature_auth.domain.entity.UserState.Initial)
    val currentUser = _currentUser.asStateFlow()

    init {
        updateUserState()
    }

    private fun updateUserState() {
        viewModelScope.launch {
            userManager.userState.collect {
                _currentUser.value = it
            }
        }
    }

    fun getMetaCardList(partySize: com.khve.feature_meta.domain.entity.PartySizeEnum) {
        viewModelScope.launch {
            _metaCardListState.value = com.khve.feature_meta.domain.entity.MetaCardListState.Progress
            getMetaCardListUseCase(partySize).collect {
                _metaCardListState.value = it
            }
        }
    }

}
