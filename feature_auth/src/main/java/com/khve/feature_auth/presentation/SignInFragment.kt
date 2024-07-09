package com.khve.feature_auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.khve.ui.R
import com.khve.ui.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignInFragment : Fragment() {

    private val viewModel: SignInViewModel by viewModels()
    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding
        get() = _binding ?: throw NullPointerException("FragmentSignInBinding == null")
    private lateinit var backPressedCallback: OnBackPressedCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBackPressed()
    }

    private fun setupBackPressed() {
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(this, backPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenButtons()
        observeViewModel()
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.authState.collect {
                    when (it) {
                        com.khve.feature_auth.domain.entity.AuthState.Initial -> {
                            signInInProgress(false)
                        }
                        is com.khve.feature_auth.domain.entity.AuthState.Error -> {
                            signInInProgress(false)
                            Toast.makeText(requireContext(), it.error, Toast.LENGTH_SHORT).show()
                        }

                        com.khve.feature_auth.domain.entity.AuthState.Progress -> {
                            signInInProgress(true)
                        }
                    }
                }
            }
        }
    }

    private fun signInInProgress(isInProgress: Boolean) {
        if (isInProgress) {
            binding.btnSignIn.isClickable = false
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.btnSignIn.isClickable = true
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun listenButtons() {
        binding.tvSignUp.setOnClickListener {
            startSignUpFragment()
        }
        binding.tvForgotPassword.setOnClickListener {
            // TODO: Forgot password
        }
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            viewModel.signInWithEmailAndPassword(email, password)
        }
    }

    private fun startSignUpFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container, SignUpFragment.newInstance())
            .addToBackStack(SignUpFragment.BACKSTACK_NAME)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        backPressedCallback.remove()
    }

    companion object {
        const val BACKSTACK_NAME = "sign_in_fragment"

        @JvmStatic
        fun newInstance() = SignInFragment()
    }
}