package com.khve.dndcompanion.presentation.main

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.khve.dndcompanion.R
import com.khve.dndcompanion.databinding.FragmentMainBinding
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.presentation.CompanionApplication
import com.khve.dndcompanion.presentation.meta.MetaListFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainFragment : Fragment() {

    @Inject
    lateinit var viewModel: MainFragmentViewModel
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw RuntimeException("FragmentMainBinding == null")

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
            startMetaListFragment()
        }
    }

    private fun startMetaListFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container, MetaListFragment.newInstance())
            .addToBackStack(MetaListFragment.BACKSTACK_NAME)
            .commit()
    }

    companion object {
        const val BACKSTACK_NAME = "main_fragment"

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}