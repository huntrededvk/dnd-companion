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
import com.khve.ui.databinding.FragmentForgotPasswordBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgotPasswordFragment : Fragment() {

    private val viewModel: ForgotPasswordViewModel by viewModels()
    private var _binding: FragmentForgotPasswordBinding? = null
    private val binding: FragmentForgotPasswordBinding
        get() = _binding ?: throw NullPointerException("FragmentForgotPasswordBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentForgotPasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelListener()
        buttonListeners()
    }

    private fun viewModelListener() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.notification.collect {
                    if (it != null) {
                        Toast.makeText(
                            requireContext(),
                            it,
                            Toast.LENGTH_SHORT
                        ).show()
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                }
            }
        }
    }

    private fun buttonListeners() {
        with(binding) {
            tvSignIn.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
            btnResetPassword.setOnClickListener {
                viewModel.resetPassword(etEmail.text.toString().trim())
            }
        }
    }


    companion object {
        const val BACKSTACK_NAME = "forgot_password_fragment"

        @JvmStatic
        fun newInstance() = ForgotPasswordFragment()
    }
}