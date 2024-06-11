package com.khve.dndcompanion.data.network

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.khve.dndcompanion.data.auth.mapper.UserMapper
import com.khve.dndcompanion.data.auth.model.UserDbDto
import com.khve.dndcompanion.domain.auth.entity.UserState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class FirebaseUserManager @Inject constructor(
    private val userMapper: UserMapper
) {

    private val instance = FirebaseAuth.getInstance()
    private val auth = Firebase.auth
    private val mDocRef = FirebaseFirestore.getInstance().collection("users")

    private val _userState = MutableStateFlow<UserState>(UserState.Initial)
    val userState = _userState.asStateFlow()

    init {
        getCurrentUserState()
    }

    private fun getCurrentUserState() {
        val currentUser = auth.currentUser
        if (currentUser != null) {
            getUserFromDbByUid(currentUser.uid)
            addUserDbListener(currentUser.uid)
            addUserAuthListener()
        } else {
            _userState.value = UserState.NotAuthorized
        }
    }

    private fun getUserFromDbByUid(userUid: String) {
        mDocRef.document(userUid).get()
            .addOnSuccessListener { snapshot ->
                val userDbDto = snapshot?.toObject(UserDbDto::class.java)
                _userState.value = if (userDbDto != null) {
                    UserState.User(userMapper.mapUserDbDtoToUser(userDbDto))
                } else {
                    UserState.Error("User's data is empty")
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
                UserState.User(userMapper.mapUserDbDtoToUser(userDbDto))
            } else {
                UserState.Error("User's data is empty")
            }
        }
    }

    private fun addUserAuthListener() {
        instance.addAuthStateListener {
            Log.d("TESTTEST", "addUserAuthListener")
            val currentUser = it.currentUser
            if (currentUser == null) {
                _userState.value = UserState.NotAuthorized
            } else {
                getUserFromDbByUid(currentUser.uid)
            }
        }
    }
}