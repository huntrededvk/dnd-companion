package com.khve.feature_main.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.navigation.NavigationView
import com.khve.feature_auth.domain.entity.UserState
import com.khve.feature_auth.presentation.SignInFragment
import com.khve.feature_auth.presentation.SignUpFragment
import com.khve.feature_meta.presentation.MetaListTabFragment
import com.khve.feature_profile.presentation.UserProfileFragment
import com.khve.ui.R
import com.khve.ui.databinding.ActivityMainBinding
import com.khve.ui.databinding.NavHeaderMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private var currentUserState: UserState = UserState.Initial
    private lateinit var binding: ActivityMainBinding
    private lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupDrawer()
        handleLastFragment()
        onBackPressedPopBack()
        observeInternetConnection()
        observeAuth(savedInstanceState != null)
        supportActionBar?.title = savedInstanceState?.getString(TOOLBAR)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(TOOLBAR, toolbar.title.toString())
    }

    private fun setupDrawer() {
        navView = binding.navView
        toolbar = binding.appBarMain.toolbar
        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(
            this, binding.drawerLayout, toolbar,
            R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_meta -> startMetaListTabFragment()
                R.id.nav_profile -> startProfileFragment()
                R.id.nav_sign_in -> startSignInFragment()
                R.id.nav_sign_out -> viewModel.signOut()
                R.id.nav_github -> startActivity(
                    Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse("https://github.com/huntrededvk/dnd-companion")
                    )
                )
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }
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

    private fun observeAuth(isActivityRestored: Boolean) {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.userState.collect {
                    currentUserState = it
                    handleUserState(it, isActivityRestored)
                }
            }
        }
    }

    private fun handleUserState(userState: UserState, isActivityRestored: Boolean) {
        val navHeaderBinding = NavHeaderMainBinding.bind(binding.navView.getHeaderView(0))
        val navSignIn = binding.navView.menu.findItem(R.id.nav_sign_in)
        val navSignOut = binding.navView.menu.findItem(R.id.nav_sign_out)
        val navUserProfile = binding.navView.menu.findItem(R.id.nav_profile)

        when (userState) {
            is UserState.Error -> Toast.makeText(
                applicationContext,
                userState.errorMessage,
                Toast.LENGTH_SHORT
            ).show()

            is UserState.User -> {
                navSignIn.isVisible = false
                navSignOut.isVisible = true
                navUserProfile.isVisible = true
                navHeaderBinding.tvUserUsername.text = userState.user.username
                navHeaderBinding.llWelcomeUser.visibility = View.VISIBLE

                if (!isActivityRestored) {
                    // startMainFragment() - Change back when new features are added
                    startMetaListTabFragment()
                }
            }

            UserState.NotAuthorized -> {
                navSignIn.isVisible = true
                navSignOut.isVisible = false
                navUserProfile.isVisible = false
                navHeaderBinding.llWelcomeUser.visibility = View.GONE
                startMetaListTabFragment()
            }

            UserState.Initial -> {}
        }
    }

    private fun handleLastFragment() {
        supportFragmentManager.addOnBackStackChangedListener {
            if (supportFragmentManager.backStackEntryCount == 0) {
                return@addOnBackStackChangedListener
            }

            val visibleFragment = supportFragmentManager.fragments.last()

            when (visibleFragment) {
                is MainFragment -> setToolbarTitle(R.string.app_name)
                is SignInFragment -> setToolbarTitle(R.string.sign_in)
                is SignUpFragment -> setToolbarTitle(R.string.sign_up)
                is MetaListTabFragment -> setToolbarTitle(R.string.meta_builds)
                is UserProfileFragment -> setToolbarTitle(R.string.profile)
            }
        }
    }

    private fun setToolbarTitle(stringResId: Int) {
        supportActionBar?.title = getString(stringResId)
    }

    private fun startMetaListTabFragment() {
        supportFragmentManager.popBackStack(MetaListTabFragment.BACKSTACK_NAME, 1)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MetaListTabFragment.newInstance())
            .addToBackStack(MetaListTabFragment.BACKSTACK_NAME)
            .commit()
    }

    private fun startProfileFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, UserProfileFragment.newInstance(null))
            .addToBackStack(UserProfileFragment.BACKSTACK_NAME)
            .commit()
    }

    private fun startErrorFragment(errorMessage: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, ErrorFragment.newInstance(errorMessage))
            .addToBackStack(ErrorFragment.BACKSTACK_NAME)
            .commit()
    }

    private fun startSignInFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, SignInFragment.newInstance())
            .addToBackStack(SignInFragment.BACKSTACK_NAME)
            .commit()
    }

    private fun startMainFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MainFragment.newInstance())
            .addToBackStack(MainFragment.BACKSTACK_NAME)
            .commit()
    }

    companion object {
        private const val TOOLBAR = "toolbar"
    }
}
