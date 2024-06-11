package com.khve.dndcompanion.presentation.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.khve.dndcompanion.R
import com.khve.dndcompanion.data.auth.model.UserSignUpDto
import com.khve.dndcompanion.databinding.FragmentSignUpBinding
import com.khve.dndcompanion.domain.auth.entity.AuthState
import com.khve.dndcompanion.presentation.CompanionApplication
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.presentation.MainActivity
import com.khve.dndcompanion.presentation.MainFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpFragment : Fragment() {

    @Inject
    lateinit var viewModel: SignUpViewModel
    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding ?: throw RuntimeException("FragmentSignUpFragment == null")

    private val component by lazy {
        (requireActivity().application as CompanionApplication).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }
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
                email = binding.etEmail.text.toString(),
                password = binding.etPassword.text.toString(),
                username = binding.etUsername.text.toString(),
                discord = binding.etDiscord.text.toString()
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
                    if (it is AuthState.Error) {
                        Toast.makeText(context, it.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun popBackToMainActivity() {
        requireActivity().supportFragmentManager
            .popBackStack(
                MainActivity.BACK_STACK_NAME,
                POP_BACK_STACK_NOT_INCLUSIVE
            )
    }

    companion object {
        private const val POP_BACK_STACK_NOT_INCLUSIVE = 0
        @JvmStatic
        fun newInstance() =
            SignUpFragment()
    }
}