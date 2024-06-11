package com.khve.dndcompanion.presentation

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.khve.dndcompanion.databinding.FragmentMainBinding
import com.khve.dndcompanion.domain.auth.entity.User

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding == null")

    private lateinit var currentUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        parseParams()
        super.onCreate(savedInstanceState)

    }

    private fun parseParams() {
        val args = requireArguments()
        if (args.containsKey(ARG_CURRENT_USER)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val userFromBundle: User? = args.getParcelable(ARG_CURRENT_USER, User::class.java)
                if (userFromBundle != null) {
                    currentUser = userFromBundle
                }
            } else {
                val userFromBundle: User? = args.getParcelable(ARG_CURRENT_USER)
                if (userFromBundle != null) {
                    currentUser = userFromBundle
                }
            }
        } else {
            throw RuntimeException("No current user passed to MainFragment params")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenViews()
        setupInitialValuesForViews()
    }

    private fun setupInitialValuesForViews() {
        binding.tvUserUsername.text = currentUser.username
    }

    private fun listenViews() {
        binding.btnSignOut.setOnClickListener {
            Firebase.auth.signOut()
            popBackToMainActivity()
        }
    }

    private fun popBackToMainActivity() {
        requireActivity().supportFragmentManager
            .popBackStack(
                MainActivity.BACK_STACK_NAME,
                1
            )
    }

    companion object {
        const val BACKSTACK_NAME = "main_fragment"
        private const val ARG_CURRENT_USER = "arg_current_user"

        @JvmStatic
        fun newInstance(currentUser: User) = MainFragment().apply {
            arguments = Bundle().apply {
                putParcelable(ARG_CURRENT_USER, currentUser)
            }
        }
    }
}