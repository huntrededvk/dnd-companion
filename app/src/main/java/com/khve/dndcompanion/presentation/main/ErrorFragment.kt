package com.khve.dndcompanion.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.khve.dndcompanion.databinding.FragmentErrorBinding

class ErrorFragment : Fragment() {

    private var errorMessage: String? = null
    private var _binding: FragmentErrorBinding? = null
    private val binding: FragmentErrorBinding
        get() = _binding ?: throw NullPointerException("FragmentErrorBinding == null")

    override fun onResume() {
        super.onResume()
        onBackPressedFinish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentErrorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvErrorMessage.text = errorMessage
    }

    private fun parseParams() {
        arguments?.let {
            errorMessage = it.getString(ERROR_MESSAGE)
        }
        if (errorMessage == null) {
            throw NoSuchElementException("No error was passed through params")
        }
    }

    private fun onBackPressedFinish() {
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    isEnabled = false
                    requireActivity().finish()
                }
            })
    }

    companion object {
        private const val ERROR_MESSAGE = "error_message"
        const val BACKSTACK_NAME = "error_fragment"

        @JvmStatic
        fun newInstance(errorMessage: String) =
            ErrorFragment().apply {
                arguments = Bundle().apply {
                    putString(ERROR_MESSAGE, errorMessage)
                }
            }
    }
}

