package com.khve.feature_main.presentation

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.khve.feature_auth.presentation.SignInFragment
import com.khve.ui.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private var currentUserState: com.khve.feature_auth.domain.entity.UserState = com.khve.feature_auth.domain.entity.UserState.Initial
    private var savedOrientation: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        savedOrientation =
            savedInstanceState?.getInt("orientation") ?: resources.configuration.orientation
        onBackPressedPopBack()
        observeInternetConnection()
        observeViewModel()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState.putInt("orientation", resources.configuration.orientation)
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

    private fun handleUserState(userState: com.khve.feature_auth.domain.entity.UserState) {
        when (userState) {
            is com.khve.feature_auth.domain.entity.UserState.Error -> Toast.makeText(
                this@MainActivity,
                userState.errorMessage,
                Toast.LENGTH_SHORT
            ).show()

            is com.khve.feature_auth.domain.entity.UserState.User -> {
                if (!isScreenRotated()) {
                    startMainFragment()
                }
            }

            com.khve.feature_auth.domain.entity.UserState.NotAuthorized -> {
                if (!isScreenRotated()) {
                    startSignInFragment()
                }
            }

            com.khve.feature_auth.domain.entity.UserState.Initial -> {}
        }
    }

    private fun isScreenRotated(): Boolean {
        val currentOrientation = resources.configuration.orientation
        val isRotated = savedOrientation != null && savedOrientation != currentOrientation
        savedOrientation = currentOrientation
        return isRotated
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

}