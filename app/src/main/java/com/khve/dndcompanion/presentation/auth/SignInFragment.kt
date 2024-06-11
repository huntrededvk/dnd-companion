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
import com.khve.dndcompanion.databinding.FragmentSignInBinding
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.presentation.CompanionApplication
import com.khve.dndcompanion.presentation.MainActivity
import com.khve.dndcompanion.presentation.MainFragment
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class SignInFragment : Fragment() {

    @Inject
    lateinit var viewModel: SignInViewModel
    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding
        get() = _binding ?: throw RuntimeException("FragmentSignInBinding == null")

    private val component by lazy {
        (requireActivity().application as CompanionApplication).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
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
        listenViews()
    }

    private fun listenViews() {
        binding.tvSignUp.setOnClickListener {
            startSignUpFragment()
        }
        binding.tvForgotPassword.setOnClickListener {
            // TODO: Forgot password
        }
        binding.btnSignIn.setOnClickListener {
            val email = binding.etEmail.text.toString()
            val password = binding.etPassword.text.toString()
            viewModel.signInWithEmailAndPassword(email, password)
        }
    }

    private fun startSignUpFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container, SignUpFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }

    companion object {
        const val BACKSTACK_NAME = "sign_in_fragment"
        private const val POP_BACK_STACK_NOT_INCLUSIVE = 0
        @JvmStatic
        fun newInstance() = SignInFragment()
    }
}