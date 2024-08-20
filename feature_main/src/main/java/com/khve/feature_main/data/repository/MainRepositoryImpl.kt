package com.khve.feature_main.data.repository

import com.khve.feature_auth.data.network.firebase.FirebaseUserManager
import com.khve.feature_auth.domain.entity.UserState
import com.khve.feature_main.domain.repository.MainRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val firebaseUserManager: FirebaseUserManager
) : MainRepository {

    override fun getCurrentUserFromDb(): StateFlow<UserState> {
        return firebaseUserManager.userState
    }

}
