package com.khve.feature_auth.data.network.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.khve.feature_auth.data.mapper.UserMapper
import com.khve.feature_auth.data.model.UserDbDto
import com.khve.feature_auth.data.model.UserSignUpDto
import com.khve.feature_auth.domain.UserRolePermissions
import com.khve.feature_auth.domain.entity.AuthState
import com.khve.feature_auth.domain.entity.Permission
import com.khve.feature_auth.domain.entity.UserRole
import com.khve.feature_auth.domain.entity.UserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FirebaseUserManager @Inject constructor(
    private val userMapper: UserMapper
) {

    private val instance = FirebaseAuth.getInstance()
    private val auth = Firebase.auth
    private val mDocRef = FirebaseFirestore.getInstance().collection("users")

    private val _userState = MutableStateFlow<UserState>(UserState.Initial)
    val userState = _userState.asStateFlow()

    private val _anotherUserState = MutableStateFlow<UserState>(UserState.Initial)
    val anotherUserState = _anotherUserState.asStateFlow()

    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    private val authState = _authState.asStateFlow()

    private val _notification = MutableStateFlow<String?>(null)
    val notification = _notification.asStateFlow()

    init {
        getCurrentUserState()
    }

    fun compareUserUidWith(uid: String) = uid == auth.uid

    fun getCurrentUserId(): String? {
        return auth.currentUser?.uid
    }

    private fun verifyCurrentUser(): StateFlow<String?> {
        val state = userState.value
        if (state !is UserState.User || state.user.role != UserRole.NOT_VERIFIED) return notification

        auth.currentUser?.reload()
            ?.addOnSuccessListener {
                if (state.user.role == UserRole.NOT_VERIFIED
                    && auth.currentUser?.isEmailVerified == true
                ) {
                    mDocRef.document(state.user.uid).update("role", UserRole.USER)
                        .addOnSuccessListener {
                            _userState.value = UserState.User(state.user.copy(role = UserRole.USER))
                            _notification.value = "Email verified"
                        }
                } else if (state.user.role == UserRole.NOT_VERIFIED) {
                    _notification.value = "Email is not verified"
                } else {
                    _notification.value = null
                }
            }

        return notification
    }

    fun hasPermission(permission: Permission): Boolean {
        verifyCurrentUser()
        val state = userState.value
        if (state !is UserState.User) return false
        return UserRolePermissions.hasPermission(state.user, permission)
    }

    fun hasOneOfRoles(roles: List<UserRole>): Boolean {
        val state = userState.value
        if (state !is UserState.User) return false
        return state.user.role in roles
    }

    fun sendEmailVerification() {
        val currentUser = auth.currentUser
        require(currentUser != null)

        currentUser.reload()
            .addOnSuccessListener {
                if (!currentUser.isEmailVerified) {
                    auth.currentUser?.sendEmailVerification()
                        ?.addOnSuccessListener {
                            _notification.value = "Email verification sent"
                        }
                        ?.addOnFailureListener { e ->
                            _notification.value = e.localizedMessage
                        }
                } else {
                    verifyCurrentUser()
                }
            }
    }

    fun createUser(userSignUpDto: UserSignUpDto): StateFlow<AuthState> {
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
                        setUserToDb(
                            userMapper.mapUserSignUpDtoToUserDbDto(userSignUpDto),
                            createdUserUid
                        )
                    }
                    sendEmailVerification()
                } else {
                    if (task.exception != null) {
                        val exception = task.exception
                        _authState.value =
                            AuthState.Error(exception?.localizedMessage ?: "Unknown error")
                    }
                }
            }

        return _authState.asStateFlow()
    }

    fun forgotPassword(email: String): StateFlow<String?> {
        auth.sendPasswordResetEmail(email)
            .addOnSuccessListener {
                _notification.value = "Recovery email sent"
            }
            .addOnFailureListener {
                _notification.value = "Failed to send recovery email"
            }

        return _notification
    }

    fun signInWithEmailAndPassword(email: String, password: String): StateFlow<AuthState> {
        if (email.isEmpty()) {
            _authState.value = AuthState.Error("Email cannot be empty")
        } else if (password.isEmpty()) {
            _authState.value = AuthState.Error("Password cannot be empty")
            return authState
        }

        auth.signInWithEmailAndPassword(email, password)
            .addOnFailureListener { e ->
                _authState.value = AuthState.Error(e.localizedMessage ?: "Unknown error")
            }

        return authState
    }

    fun signOut() {
        auth.signOut()
    }

    private fun getCurrentDbUserState(): UserState {
        val currentDbUserState = userState.value
        return if (currentDbUserState is UserState.User) {
            currentDbUserState
        } else {
            UserState.NotAuthorized
        }
    }

    private fun setUserToDb(user: UserDbDto, userUid: String) {
        mDocRef.document(userUid)
            .set(user)
            .addOnFailureListener { e ->
                auth.currentUser?.delete()
                _authState.value = AuthState.Error(e.message.toString())
            }
    }

    private fun getCurrentUserState() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            getUserFromDbByUid(currentUser.uid, false)
            addUserDbListener(currentUser.uid)
        } else {
            _userState.value = UserState.NotAuthorized
        }
        addUserAuthListener()
    }

    fun getUserFromDbByUid(userUid: String, isAnotherUser: Boolean) {
        mDocRef.document(userUid).get()
            .addOnSuccessListener { snapshot ->
                val userDbDto = snapshot?.toObject(UserDbDto::class.java)
                val userState = if (userDbDto != null) {
                    val mappedUser = userMapper.mapUserDbDtoToUser(userDbDto, userUid)
                    UserState.User(mappedUser)
                } else {
                    UserState.Error("User's data is empty")
                }
                if (isAnotherUser) {
                    _anotherUserState.value = userState
                } else {
                    _userState.value = userState
                }
            }
            .addOnFailureListener {
                _userState.value = UserState.Error(
                    it.localizedMessage ?: "Unknown error"
                )
            }
    }

    private fun addUserDbListener(userUid: String) {
        mDocRef.document(userUid).addSnapshotListener { snapshot, e ->
            if (e != null) {
                _userState.value = UserState.Error(e.localizedMessage ?: "Unknown error")
                return@addSnapshotListener
            }

            val userDbDto = snapshot?.toObject(UserDbDto::class.java)
            _userState.value = if (userDbDto != null) {
                UserState.User(userMapper.mapUserDbDtoToUser(userDbDto, userUid))
            } else {
                UserState.Error("User's data is empty")
            }
        }
    }

    private fun addUserAuthListener() {
        instance.addAuthStateListener {
            val currentUser = it.currentUser
            if (currentUser == null) {
                _userState.value = UserState.NotAuthorized
            } else {
                getUserFromDbByUid(currentUser.uid, false)
            }
        }
    }

    private fun isValidated(userSignUpDto: UserSignUpDto): Boolean {
        var error = ""

        val userToValidate = userSignUpDto.copy(
            email = userSignUpDto.email,
            password = userSignUpDto.password,
            username = userSignUpDto.username,
            discord = userSignUpDto.discord
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
            _authState.value = AuthState.Error(error)
            return false
        }

        return true
    }
}
