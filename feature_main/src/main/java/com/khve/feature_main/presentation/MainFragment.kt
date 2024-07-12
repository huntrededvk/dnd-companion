package com.khve.feature_main.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.khve.feature_meta.presentation.MetaListTabFragment
import com.khve.ui.R
import com.khve.ui.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainFragmentViewModel by viewModels()
    private var _binding: FragmentMainBinding? = null
    private val binding: FragmentMainBinding
        get() = _binding ?: throw NullPointerException("FragmentMainBinding == null")

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
                    if (it is com.khve.feature_auth.domain.entity.UserState.User) {
                        binding.tvUserUsername.text = it.user.username
                    }
                }
            }
        }
    }

    private fun listenViews() {
        binding.btnSignOut.setOnClickListener {
            viewModel.signOut()
        }
        binding.clMeta.setOnClickListener {
            startMetaListTabFragment()
        }
    }

    private fun startMetaListTabFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, MetaListTabFragment.newInstance())
            .addToBackStack(MetaListTabFragment.BACKSTACK_NAME)
            .commit()
    }

    companion object {
        const val BACKSTACK_NAME = "main_fragment"

        @JvmStatic
        fun newInstance() = MainFragment()
    }
}
