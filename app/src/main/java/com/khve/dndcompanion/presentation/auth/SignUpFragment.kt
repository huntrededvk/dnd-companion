package com.khve.dndcompanion.presentation.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.khve.dndcompanion.R
import com.khve.dndcompanion.data.auth.model.UserSignUpDto
import com.khve.dndcompanion.databinding.FragmentAuthBinding
import com.khve.dndcompanion.presentation.CompanionApplication
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.presentation.MainFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignUpFragment : Fragment() {

    @Inject
    lateinit var viewModel: AuthViewModel
    private var _binding: FragmentAuthBinding? = null
    private val binding: FragmentAuthBinding
        get() = _binding ?: throw RuntimeException("FragmentShopItemBinding == null")

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
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
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
                viewModel.userState.collect {
                    when (it) {
                        is UserState.Authorized -> startMainFragment()
                        is UserState.Error -> Toast.makeText(context, it.errorMessage, Toast.LENGTH_SHORT).show()
                            UserState.NotAuthorized -> {}
                            UserState.Progress -> {}
                            UserState.Initial -> {}
                    }
                }
            }
        }
    }

    private fun startMainFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container, MainFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SignUpFragment()
    }
}