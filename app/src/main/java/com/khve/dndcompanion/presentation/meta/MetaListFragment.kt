package com.khve.dndcompanion.presentation.meta

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.khve.dndcompanion.R
import com.khve.dndcompanion.databinding.FragmentMetaListBinding
import com.khve.dndcompanion.domain.auth.entity.User
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.auth.enum.Permission
import com.khve.dndcompanion.domain.meta.entity.MetaBuildEnum
import com.khve.dndcompanion.domain.meta.entity.MetaCardItem
import com.khve.dndcompanion.domain.meta.entity.MetaCardListState
import com.khve.dndcompanion.domain.meta.entity.MetaTypeEnum
import com.khve.dndcompanion.presentation.CompanionApplication
import com.khve.dndcompanion.presentation.meta.adapter.MetaListAdapter
import kotlinx.coroutines.launch
import javax.inject.Inject

class MetaListFragment : Fragment() {

    @Inject
    lateinit var viewModel: MetaListViewModel
    private var _binding: FragmentMetaListBinding? = null
    private val binding: FragmentMetaListBinding
        get() = _binding ?: throw NullPointerException("FragmentMetaListBinding == null")

    private lateinit var metaListAdapter: MetaListAdapter
    private var metaType = MetaTypeEnum.INITIAL
    private var metaBuild = MetaBuildEnum.INITIAL

    private val component by lazy {
        (requireActivity().application as CompanionApplication).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        getMetaInfo()
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
    private fun getMetaInfo() {
        // TODO: Remove and move logic to parse params
        metaType = MetaTypeEnum.BUILD
        metaBuild = MetaBuildEnum.SOLO
        viewModel.getMetaCardList(metaType, metaBuild)
    }

    private fun isOnPaneMode(): Boolean {
        return binding.fcvMetaItemContainer != null
    }

    private fun buttonListeners(currentUser: User) {
        if (currentUser.hasPermission(Permission.ADD_META_ITEM)) {
            binding.fabAddMetaItem.visibility = View.VISIBLE
            binding.fabAddMetaItem.isClickable = true
            binding.fabAddMetaItem.setOnClickListener {
                startAddMetaItemFragment()
            }
        }
    }

    private fun startAddMetaItemFragment() {
        if (isOnPaneMode()) {
            replaceFragment(AddMetaItemFragment.newInstance(), R.id.fcv_meta_item_container)
        } else {
            replaceFragment(AddMetaItemFragment.newInstance(), R.id.auth_container)
        }
    }

    private fun startMetaItemFragment(metaCardItem: MetaCardItem) {
        if (isOnPaneMode()) {
            replaceFragment(
                MetaItemFragment.newInstance(metaCardItem),
                R.id.fcv_meta_item_container
            )
        } else {
            replaceFragment(MetaItemFragment.newInstance(metaCardItem), R.id.auth_container)
        }
    }

    private fun replaceFragment(fragment: Fragment, containerId: Int) {
        with(requireActivity().supportFragmentManager) {
            popBackStack(BACKSTACK_NAME, 0)
            beginTransaction()
                .replace(containerId, fragment)
                .addToBackStack(null)
                .commit()
        }
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
                viewModel.metaCardListState.collect {
                    when(it) {
                        MetaCardListState.Initial -> {}
                        is MetaCardListState.MetaCardList -> {
                            val sortedMetaList = it.metaCardList.sortedBy {
                                resources.getStringArray(R.array.tiers).indexOf(it.tier)
                            }
                            metaListAdapter.submitList(sortedMetaList)
                            loadMetaListInProgress(false)
                        }
                        is MetaCardListState.Error -> {
                            loadMetaListInProgress(false)
                            Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                        }

                        MetaCardListState.Progress -> {
                            loadMetaListInProgress(true)
                        }
                    }
                }
            }
        }

        metaListAdapter.onMetaItemClickListener = {
            startMetaItemFragment(it)
        }
    }

    private fun loadMetaListInProgress(isInProgress: Boolean) {
        if (isInProgress) {
            metaListAdapter.onMetaItemClickListener = {}
            binding.progressBarMetaList.visibility = View.VISIBLE
        } else {
            metaListAdapter.onMetaItemClickListener = {
                startMetaItemFragment(it)
            }
            binding.progressBarMetaList.visibility = View.INVISIBLE
        }
    }

    private fun setupRecyclerView() {
        with(binding.rvMetaList) {
            metaListAdapter = MetaListAdapter(requireContext())
            adapter = metaListAdapter
        }
    }


    companion object {
        const val BACKSTACK_NAME = "meta_list_fragment"

        @JvmStatic
        fun newInstance() = MetaListFragment()
    }
}
