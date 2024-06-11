package com.khve.dndcompanion.data

import com.khve.dndcompanion.data.network.FirebaseUserManager
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.repository.MainRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val firebaseUserManager: FirebaseUserManager
) : MainRepository {

    override fun getCurrentUserFromDb(): StateFlow<UserState> {
        return firebaseUserManager.userState
    }

}