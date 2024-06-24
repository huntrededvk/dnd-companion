package com.khve.dndcompanion.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.khve.dndcompanion.R
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.presentation.CompanionApplication
import com.khve.dndcompanion.presentation.auth.SignInFragment
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
        onBackPressedPopBack()
        observeInternetConnection()
        component.inject(this)
        observeViewModel()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun onBackPressedPopBack() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                supportFragmentManager.popBackStack()
            }
        })
    }

    private fun observeInternetConnection() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.internetState.collect {
                    if (it) {
                        supportFragmentManager.popBackStack(ErrorFragment.BACKSTACK_NAME, 1)
                        viewModel.getCurrentUser()
                    } else {
                        startErrorFragment("Please, check your internet connection!")
                    }
                }
            }
        }
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
            is UserState.Error -> Toast.makeText(
                this@MainActivity,
                userState.errorMessage,
                Toast.LENGTH_SHORT
            ).show()

            is UserState.User -> {
                startMainFragment()
            }

            UserState.NotAuthorized -> {
                startSignInFragment()
            }

            UserState.Initial -> {}
        }
    }

    private fun startErrorFragment(errorMessage: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container, ErrorFragment.newInstance(errorMessage))
            .addToBackStack(ErrorFragment.BACKSTACK_NAME)
            .commit()
    }

    private fun startSignInFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container, SignInFragment.newInstance())
            .addToBackStack(SignInFragment.BACKSTACK_NAME)
            .commit()
    }

    private fun startMainFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container, MainFragment.newInstance())
            .addToBackStack(MainFragment.BACKSTACK_NAME)
            .commit()
    }


    companion object {
        private const val CURRENT_USER = "current_user"
    }

}
