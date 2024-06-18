package com.khve.dndcompanion.data.auth.repository

import com.khve.dndcompanion.data.auth.model.UserSignUpDto
import com.khve.dndcompanion.data.network.firebase.FirebaseUserManager
import com.khve.dndcompanion.domain.auth.entity.AuthState
import com.khve.dndcompanion.domain.auth.repository.SingUpRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SingUpRepositoryImpl @Inject constructor(
    private val firebaseUserManager: FirebaseUserManager
) : SingUpRepository {
    override fun createUser(userSignUpDto: UserSignUpDto): StateFlow<AuthState> =
        firebaseUserManager.createUser(userSignUpDto)
}