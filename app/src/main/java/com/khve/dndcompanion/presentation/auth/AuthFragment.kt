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
import com.khve.dndcompanion.data.auth.model.UserSignUpDto
import com.khve.dndcompanion.databinding.FragmentAuthBinding
import com.khve.dndcompanion.presentation.CompanionApplication
import com.khve.dndcompanion.domain.auth.entity.UserState
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthFragment : Fragment() {

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
    ): View? {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        addViewListeners()
    }

    private fun addViewListeners() {
        binding.btnSignUp.setOnClickListener() {
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
                        is UserState.Authorized -> Toast.makeText(context, "Authorized", Toast.LENGTH_SHORT).show()
                        is UserState.Error -> Toast.makeText(context, it.errorMessage.toString(), Toast.LENGTH_SHORT).show()
                            UserState.NotAuthorized -> Toast.makeText(context, "Not Authorized", Toast.LENGTH_SHORT).show()
                            UserState.Progress -> Toast.makeText(context, "Progress", Toast.LENGTH_SHORT).show()
                        else -> {
                            Toast.makeText(context, "Unknown error", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AuthFragment()
    }
}