package com.khve.feature_auth.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.khve.feature_auth.data.model.UserSignUpDto
import com.khve.feature_auth.domain.entity.AuthState
import com.khve.ui.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModels()
    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding ?: throw NullPointerException("FragmentSignUpBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        addViewListeners()
    }

    private fun addViewListeners() {
        binding.btnSignUp.setOnClickListener {
            val userSignUpDto = UserSignUpDto(
                email = binding.etEmail.text.toString().trim(),
                password = binding.etPassword.text.toString().trim(),
                username = binding.etUsername.text.toString().trim(),
                discord = binding.etDiscord.text.toString().trim()
            )

            viewModel.createUser(userSignUpDto)
        }
    }

    private fun observeViewModel() {
        observeAuth()
    }

    private fun observeAuth() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.authState.collect {
                    when (it) {
                        is AuthState.Error -> {
                            signUpInProgress(false)
                            Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                        }
                        AuthState.Initial -> signUpInProgress(false)
                        AuthState.Progress -> signUpInProgress(true)
                    }
                }
            }
        }
    }

    private fun signUpInProgress(isInProgress: Boolean) {
        if (isInProgress) {
            binding.btnSignUp.isClickable = false
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.btnSignUp.isClickable = true
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    companion object {
        const val BACKSTACK_NAME = "sign_up_fragment"

        @JvmStatic
        fun newInstance() =
            SignUpFragment()
    }
}
