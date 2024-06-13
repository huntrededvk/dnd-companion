package com.khve.dndcompanion.data.auth.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.khve.dndcompanion.data.auth.mapper.UserMapper
import com.khve.dndcompanion.data.auth.model.UserDbDto
import com.khve.dndcompanion.data.auth.model.UserSignUpDto
import com.khve.dndcompanion.data.network.FirebaseUserManager
import com.khve.dndcompanion.domain.auth.entity.AuthState
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.auth.repository.SingUpRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SingUpRepositoryImpl @Inject constructor(
    private val firebaseUserManager: FirebaseUserManager
): SingUpRepository {
    override fun createUser(userSignUpDto: UserSignUpDto) : StateFlow<AuthState> {
        return firebaseUserManager.createUser(userSignUpDto)
    }
}