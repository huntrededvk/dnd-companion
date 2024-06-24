package com.khve.dndcompanion.data.main.repository

import com.khve.dndcompanion.data.network.firebase.FirebaseUserManager
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.main.repository.MainRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val firebaseUserManager: FirebaseUserManager
) : MainRepository {

    override fun getCurrentUserFromDb(): StateFlow<UserState> {
        return firebaseUserManager.userState
    }

}
