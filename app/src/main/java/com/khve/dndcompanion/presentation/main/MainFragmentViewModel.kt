package com.khve.dndcompanion.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.khve.dndcompanion.data.network.firebase.auth.FirebaseUserManager
import com.khve.dndcompanion.domain.auth.entity.UserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragmentViewModel @Inject constructor(
    private val userManager: FirebaseUserManager
) : ViewModel() {

    private val _currentUser = MutableStateFlow<UserState>(UserState.Initial)
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
}
