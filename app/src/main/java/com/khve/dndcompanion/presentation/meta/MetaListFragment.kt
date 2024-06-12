package com.khve.dndcompanion.presentation.meta

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.khve.dndcompanion.R
import com.khve.dndcompanion.databinding.FragmentMetaListBinding
import com.khve.dndcompanion.domain.auth.entity.User
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.auth.enum.Permission
import com.khve.dndcompanion.domain.meta.entity.MetaListState
import com.khve.dndcompanion.presentation.CompanionApplication
import com.khve.dndcompanion.presentation.meta.adapter.MetaListAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class MetaListFragment : Fragment() {

    @Inject
    lateinit var viewModel: MetaListViewModel
    private var _binding: FragmentMetaListBinding? = null
    private val binding: FragmentMetaListBinding
        get() = _binding ?: throw RuntimeException("FragmentMetaListFragment == null")

    private lateinit var metaListAdapter: MetaListAdapter

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
        _binding = FragmentMetaListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeUser()
        observeMetaList()
    }

    private fun buttonListeners(currentUser: User) {
        if (currentUser.hasPermission(Permission.ADD_META_ITEM)) {
            binding.fabAddMetaItem?.visibility = View.VISIBLE
            binding.fabAddMetaItem?.isClickable = true
            binding.fabAddMetaItem?.setOnClickListener {
                startAddMetaItemActivity()
            }
        }
    }

    private fun startAddMetaItemActivity() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.auth_container, AddMetaItemFragment.newInstance())
            .addToBackStack(AddMetaItemFragment.BACKSTACK_NAME)
            .commit()
    }

    private fun observeUser() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.currentUser.collect {
                    if (it is UserState.User) {
                        val currentUser = it.user
                        buttonListeners(currentUser)
                    }
                }
            }
        }
    }
    private fun observeMetaList() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                // Collect Meta list
                viewModel.metaList.collect {
                    if (it is MetaListState.MetaList) {
                        metaListAdapter.submitList(it.metaList)
                    } else if (it is MetaListState.Error) {
                        Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvMetaList) {
            metaListAdapter = MetaListAdapter()
            adapter = metaListAdapter
        }

        setupMetaItemClickListener()
    }

    private fun setupMetaItemClickListener() {
        // TODO
    }


    companion object {
        const val BACKSTACK_NAME = "meta_list_fragment"
        @JvmStatic
        fun newInstance() = MetaListFragment()
    }
}