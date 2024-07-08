package com.khve.dndcompanion.presentation.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.khve.dndcompanion.R
import com.khve.dndcompanion.databinding.FragmentMainBinding
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.presentation.meta.MetaListTabFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainFragmentViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw NullPointerException("FragmentMainBinding == null")
    private lateinit var backPressedCallback: OnBackPressedCallback

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
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        listenViews()

    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.currentUser.collect {
                    if (it is UserState.User) {
                        binding.tvUserUsername.text = it.user.username
                    }
                }
            }
        }
    }

    private fun listenViews() {
        binding.btnSignOut.setOnClickListener {
            Firebase.auth.signOut()
        }
        binding.clMeta.setOnClickListener {
            startMetaListTabFragment()
        }
    }

    private fun startMetaListTabFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container, MetaListTabFragment.newInstance())
            .addToBackStack(MetaListTabFragment.BACKSTACK_NAME)
            .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        backPressedCallback.remove()
    }

    companion object {
        const val BACKSTACK_NAME = "main_fragment"

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
