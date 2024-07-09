package com.khve.feature_auth.data.network.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseUserManager @Inject constructor(
    private val userMapper: com.khve.feature_auth.data.mapper.UserMapper
) {

    private val instance = FirebaseAuth.getInstance()
    private val auth = Firebase.auth
    private val mDocRef = FirebaseFirestore.getInstance().collection("users")

    private val _userState = MutableStateFlow<com.khve.feature_auth.domain.entity.UserState>(com.khve.feature_auth.domain.entity.UserState.Initial)
    val userState = _userState.asStateFlow()

    private val _authState = MutableStateFlow<com.khve.feature_auth.domain.entity.AuthState>(com.khve.feature_auth.domain.entity.AuthState.Initial)
    val authState = _authState.asStateFlow()

    init {
        getCurrentUserState()
    }

    fun getCurrentDbUserState(): com.khve.feature_auth.domain.entity.UserState {
        val currentDbUserState = userState.value
        return if (currentDbUserState is com.khve.feature_auth.domain.entity.UserState.User) {
            currentDbUserState
        } else {
            com.khve.feature_auth.domain.entity.UserState.NotAuthorized
        }
    }

    fun createUser(userSignUpDto: com.khve.feature_auth.data.model.UserSignUpDto): StateFlow<com.khve.feature_auth.domain.entity.AuthState> {
        _authState.value = com.khve.feature_auth.domain.entity.AuthState.Progress
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
                        setUserToDb(
                            userMapper.mapUserSignUpDtoToUserDbDto(userSignUpDto),
                            createdUserUid
                        )
                    }
                } else {
                    if (task.exception != null) {
                        val exception = task.exception
                        _authState.value =
                            com.khve.feature_auth.domain.entity.AuthState.Error(exception?.localizedMessage ?: "Unknown error")
                    }
                }
            }

        return _authState.asStateFlow()
    }

    fun signInWithEmailAndPassword(email: String, password: String): StateFlow<com.khve.feature_auth.domain.entity.AuthState> {
        if (email.isEmpty()) {
            _authState.value = com.khve.feature_auth.domain.entity.AuthState.Error("Email cannot be empty")
            return authState
        } else if (password.isEmpty()) {
            _authState.value = com.khve.feature_auth.domain.entity.AuthState.Error("Password cannot be empty")
            return authState
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnFailureListener { e ->
                _authState.value = com.khve.feature_auth.domain.entity.AuthState.Error(e.localizedMessage ?: "Unknown error")
            }

        return authState
    }

    fun signOut() {
        auth.signOut()
    }

    private fun setUserToDb(user: com.khve.feature_auth.data.model.UserDbDto, userUid: String) {
        mDocRef.document(userUid)
            .set(user)
            .addOnFailureListener { e ->
                auth.currentUser?.delete()
                _authState.value = com.khve.feature_auth.domain.entity.AuthState.Error(e.message.toString())
            }
    }

    private fun getCurrentUserState() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            getUserFromDbByUid(currentUser.uid)
            addUserDbListener(currentUser.uid)
        } else {
            _userState.value = com.khve.feature_auth.domain.entity.UserState.NotAuthorized
        }
        addUserAuthListener()
    }

    private fun getUserFromDbByUid(userUid: String) {
        mDocRef.document(userUid).get()
            .addOnSuccessListener { snapshot ->
                val userDbDto = snapshot?.toObject(com.khve.feature_auth.data.model.UserDbDto::class.java)
                _userState.value = if (userDbDto != null) {
                    com.khve.feature_auth.domain.entity.UserState.User(userMapper.mapUserDbDtoToUser(userDbDto, userUid))
                } else {
                    com.khve.feature_auth.domain.entity.UserState.Error("User's data is empty")
                }
            }
            .addOnFailureListener {
                _userState.value = com.khve.feature_auth.domain.entity.UserState.Error(
                    it.localizedMessage ?: "Unknown error"
                )
            }
    }

    private fun addUserDbListener(userUid: String) {
        mDocRef.document(userUid).addSnapshotListener { snapshot, e ->
            if (e != null) {
                _userState.value = com.khve.feature_auth.domain.entity.UserState.Error(e.localizedMessage ?: "Unknown error")
                return@addSnapshotListener
            }

            val userDbDto = snapshot?.toObject(com.khve.feature_auth.data.model.UserDbDto::class.java)
            _userState.value = if (userDbDto != null) {
                com.khve.feature_auth.domain.entity.UserState.User(userMapper.mapUserDbDtoToUser(userDbDto, userUid))
            } else {
                com.khve.feature_auth.domain.entity.UserState.Error("User's data is empty")
            }
        }
    }

    private fun addUserAuthListener() {
        instance.addAuthStateListener {
            val currentUser = it.currentUser
            if (currentUser == null) {
                _userState.value = com.khve.feature_auth.domain.entity.UserState.NotAuthorized
            } else {
                getUserFromDbByUid(currentUser.uid)
            }
        }
    }

    private fun isValidated(validateUserSignUpDto: com.khve.feature_auth.data.model.UserSignUpDto): Boolean {
        var error = ""

        val userToValidate = validateUserSignUpDto.copy(
            email = validateUserSignUpDto.email,
            password = validateUserSignUpDto.password,
            username = validateUserSignUpDto.username,
            discord = validateUserSignUpDto.discord
        )

        if (userToValidate.email.isEmpty()) {
            error = "Email can not be empty"
        }
        if (userToValidate.password.isEmpty()) {
            error = "Password can not be empty"
        }

        with(userToValidate.username) {
            if (isEmpty())
                error = "Username can not be empty"
            else if (length < 3)
                error = "Username is too short"
            else if (length > 20)
                error = "Username is too long"
        }

        with(userToValidate.discord) {
            if (isEmpty())
                error = "Discord username can not be empty"
            if (length > 32)
                error = "Discord username is too long"
        }

        if (error.isNotEmpty()) {
            _authState.value = com.khve.feature_auth.domain.entity.AuthState.Error(error)
            return false
        }

        return true
    }
}