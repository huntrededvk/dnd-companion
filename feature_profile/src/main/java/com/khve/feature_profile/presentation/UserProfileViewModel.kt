package com.khve.feature_profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khve.feature_auth.data.network.firebase.FirebaseUserManager
import com.khve.feature_auth.domain.entity.UserState
import com.khve.feature_meta.data.network.firebase.FirebaseMetaManager
import com.khve.feature_meta.domain.entity.MetaCardListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserProfileViewModel @Inject constructor(
private val userManager: FirebaseUserManager,
private val metaManager: FirebaseMetaManager
) : ViewModel() {
    private val _userState = MutableStateFlow<UserState>(UserState.Initial)
    val userState = _userState.asStateFlow()

    private val _metaCardListState = MutableStateFlow<MetaCardListState>(
        MetaCardListState.Initial
    )
    val metaCardListState = _metaCardListState.asStateFlow()

    private val _notification = MutableStateFlow<String?>(null)
    val notification = _notification.asStateFlow()

    fun getUserMetaCardList(userUid: String?) {
        viewModelScope.launch {
            metaManager.getUserMetaCardList(userUid).collect {
                _metaCardListState.value = it
            }
        }
    }

    fun sendVerificationEmail() {
        _notification.value = userManager.notification.value
        userManager.sendEmailVerification()
    }

    fun findAnotherUserByUid(userUid: String) {
        viewModelScope.launch {
            userManager.getUserFromDbByUid(userUid, true)
            userManager.anotherUserState.collect {
                _userState.value = it
            }
        }
    }

    fun findCurrentUserByUid() {
        viewModelScope.launch {
            val currentUserUid = userManager.getCurrentUserId()
            if (currentUserUid != null) {
                userManager.getUserFromDbByUid(currentUserUid, false)
                userManager.userState.collect {
                    _userState.value = it
                }
            }
        }
    }
}