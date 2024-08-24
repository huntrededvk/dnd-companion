package com.khve.feature_meta.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khve.feature_auth.data.network.firebase.FirebaseUserManager
import com.khve.feature_auth.domain.entity.Permission
import com.khve.feature_auth.domain.entity.UserState
import com.khve.feature_meta.domain.entity.MetaCardListState
import com.khve.feature_meta.domain.entity.PartySizeEnum
import com.khve.feature_meta.domain.usecase.GetMetaCardListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
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
    }

    fun verifyUserAndPermission(permission: Permission): Boolean {
        return userManager.hasPermission(permission)
    }

    private fun updateUserState() {
        viewModelScope.launch {
            userManager.userState.collect {
                _currentUser.value = it
            }
        }
    }

    fun getMetaCardList(partySize: PartySizeEnum) {
        viewModelScope.launch {
            _metaCardListState.value = MetaCardListState.Progress
            getMetaCardListUseCase(partySize).collect {
                _metaCardListState.value = it
            }
        }
    }

}
