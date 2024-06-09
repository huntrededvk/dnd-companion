package com.khve.dndcompanion.data.auth.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.khve.dndcompanion.data.auth.mapper.UserMapper
import com.khve.dndcompanion.data.auth.model.UserDto
import com.khve.dndcompanion.data.auth.model.UserSignUpDto
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.auth.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userMapper: UserMapper
): UserRepository {

    private val auth = Firebase.auth
    private val mDocRef = FirebaseFirestore.getInstance().collection("users")

    private val userState = MutableStateFlow<UserState>(UserState.Initial)
    override suspend fun createUser(userSignUpDto: UserSignUpDto) : StateFlow<UserState> {
        userState.value = UserState.Progress
        // Validate user
        if (!isValidated(userSignUpDto))
            return userState
        // Attempt to create user
        auth.createUserWithEmailAndPassword(userSignUpDto.email, userSignUpDto.password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Get nullable uid of created user
                    val createdUserUid = task.result.user?.uid
                    if (createdUserUid != null) {
                        // Save created user to db by uid
                        setUserToDb(userMapper.mapUserSignUpDtoToUserDto(userSignUpDto), createdUserUid)
                    }
                } else {
                    if (task.exception != null) {
                        // TODO: Make it clean
                        userState.value = UserState.Error(task.exception!!.message.toString())
                    }
                }
            }

        return userState.asStateFlow()
    }

    fun deleteUser() {
        if (checkAuthorization()) {
            auth.currentUser!!.delete()
        }
    }

    private fun setUserToDb(user: UserDto, userUid: String) {
        mDocRef.document(userUid)
            .set(user)
            .addOnSuccessListener {
                checkAuthorization()
            }
            .addOnFailureListener { e ->
                deleteUser()
                userState.value = UserState.Error(e.message.toString())
            }
    }

    private fun checkAuthorization(): Boolean {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            userState.value = UserState.Authorized(currentUser)
            return true
        } else {
            userState.value = UserState.NotAuthorized
            return false
        }
    }

    private fun isValidated(validateUserSignUpDto: UserSignUpDto): Boolean {
        var error = ""

        val userToValidate = validateUserSignUpDto.copy(
            email = validateUserSignUpDto.email.trim(),
            username = validateUserSignUpDto.username.trim(),
            discord = validateUserSignUpDto.discord.trim()
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
            userState.value = UserState.Error(error)
            return false
        }

        return true
    }
}