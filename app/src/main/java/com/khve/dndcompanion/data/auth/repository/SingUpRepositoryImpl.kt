package com.khve.dndcompanion.data.auth.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.khve.dndcompanion.data.auth.mapper.UserMapper
import com.khve.dndcompanion.data.auth.model.UserDbDto
import com.khve.dndcompanion.data.auth.model.UserSignUpDto
import com.khve.dndcompanion.domain.auth.entity.AuthState
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.auth.repository.SingUpRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class SingUpRepositoryImpl @Inject constructor(
    private val userMapper: UserMapper
): SingUpRepository {

    private val auth = Firebase.auth
    private val mDocRef = FirebaseFirestore.getInstance().collection("users")

    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    private val authState = _authState.asStateFlow()
    override fun createUser(userSignUpDto: UserSignUpDto) : StateFlow<AuthState> {
        _authState.value = AuthState.Progress
        // Validate user
        if (!isValidated(userSignUpDto))
            return authState
        // Attempt to create user
        auth.createUserWithEmailAndPassword(userSignUpDto.email, userSignUpDto.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Get nullable uid of created user
                    val createdUserUid = task.result.user?.uid
                    if (createdUserUid != null) {
                        // Save created user to db by uid
                        setUserToDb(userMapper.mapUserSignUpDtoToUserDbDto(userSignUpDto), createdUserUid)
                    }
                } else {
                    if (task.exception != null) {
                        val exception = task.exception
                        _authState.value = AuthState.Error(exception?.localizedMessage ?: "Unknown error")
                    }
                }
            }

        return _authState.asStateFlow()
    }

    private fun setUserToDb(user: UserDbDto, userUid: String) {
        mDocRef.document(userUid)
            .set(user)
            .addOnFailureListener { e ->
                auth.currentUser?.delete()
                _authState.value = AuthState.Error(e.message.toString())
            }
    }

    private fun isValidated(validateUserSignUpDto: UserSignUpDto): Boolean {
        var error = ""

        val userToValidate = validateUserSignUpDto.copy(
            email = validateUserSignUpDto.email,
            username = validateUserSignUpDto.username,
            discord = validateUserSignUpDto.discord
        )

        userToValidate.username.let {
            if (it.isEmpty())
                error = "Username can not be empty"
            else if (it.length < 3)
                error = "Username is too short"
            else if (it.length > 24)
                error = "Username is too long"
        }

        userToValidate.discord.let {
            if (it.isEmpty())
                error = "Discord username can not be empty"
            if (it.length > 32)
                error = "Discord username is too long"
        }

        if (error.isNotEmpty()) {
            _authState.value = AuthState.Error(error)
            return false
        }

        return true
    }
}