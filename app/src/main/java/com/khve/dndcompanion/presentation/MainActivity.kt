package com.khve.dndcompanion.presentation

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.khve.dndcompanion.R
import com.khve.dndcompanion.domain.auth.entity.User
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.presentation.auth.SignInFragment
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel
    private var currentUserState: UserState = UserState.Initial

    private val component by lazy {
        (application as CompanionApplication).component
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        observeViewModel()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    private fun observeViewModel() {
        observeAuth()
    }

    private fun observeAuth() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.userState.collect {
                    currentUserState = it
                    handleUserState(it)
                }
            }
        }
    }

    private fun handleUserState(userState: UserState) {
        when (userState) {
            is UserState.Authorized -> Log.d("MainActivity", "Activity: Authorized")
            is UserState.Error -> Toast.makeText(
                this@MainActivity,
                userState.errorMessage,
                Toast.LENGTH_SHORT
            ).show()
            is UserState.User -> startMainFragment(userState.user)
            UserState.NotAuthorized -> startSignInFragment()
            UserState.Progress -> {}
            UserState.Initial -> {}
        }
    }

    private fun startSignInFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container, SignInFragment.newInstance())
            .addToBackStack(SignInFragment.BACKSTACK_NAME)
            .commit()
    }

    private fun startMainFragment(user: User) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container, MainFragment.newInstance(user))
            .addToBackStack(MainFragment.BACKSTACK_NAME)
            .commit()
    }

    companion object {
        const val BACK_STACK_NAME = "main_activity"
    }

}