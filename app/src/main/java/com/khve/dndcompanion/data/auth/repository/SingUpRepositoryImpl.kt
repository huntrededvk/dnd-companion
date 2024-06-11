package com.khve.dndcompanion.data.auth.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.khve.dndcompanion.data.auth.mapper.UserMapper
import com.khve.dndcompanion.data.auth.model.UserDbDto
import com.khve.dndcompanion.data.auth.model.UserSignUpDto
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

    private val _userState = MutableStateFlow<UserState>(UserState.Initial)
    private val userState = _userState.asStateFlow()
    override fun createUser(userSignUpDto: UserSignUpDto) : StateFlow<UserState> {
        _userState.value = UserState.Progress
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
                        setUserToDb(userMapper.mapUserSignUpDtoToUserDbDto(userSignUpDto), createdUserUid)
                    }
                } else {
                    if (task.exception != null) {
                        // TODO: Make it clean
                        _userState.value = UserState.Error(task.exception!!.message.toString())
                    }
                }
            }

        return _userState.asStateFlow()
    }

    private fun setUserToDb(user: UserDbDto, userUid: String) {
        mDocRef.document(userUid)
            .set(user)
            .addOnSuccessListener {
                _userState.value = UserState.Authorized
            }
            .addOnFailureListener { e ->
                auth.currentUser?.delete()
                _userState.value = UserState.Error(e.message.toString())
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
            _userState.value = UserState.Error(error)
            return false
        }

        return true
    }
}