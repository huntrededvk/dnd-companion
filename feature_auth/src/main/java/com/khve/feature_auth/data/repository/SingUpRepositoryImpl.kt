package com.khve.feature_auth.data.repository

import com.khve.feature_auth.data.model.UserSignUpDto
import com.khve.feature_auth.data.network.firebase.FirebaseUserManager
import com.khve.feature_auth.domain.entity.AuthState
import com.khve.feature_auth.domain.repository.SingUpRepository
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class SingUpRepositoryImpl @Inject constructor(
    private val firebaseUserManager: FirebaseUserManager
) : SingUpRepository {
    override fun createUser(userSignUpDto: UserSignUpDto): StateFlow<AuthState> =
        firebaseUserManager.createUser(userSignUpDto)
}
