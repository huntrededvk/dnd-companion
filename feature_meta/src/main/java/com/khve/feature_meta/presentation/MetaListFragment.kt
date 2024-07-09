package com.khve.feature_meta.presentation

import android.os.Build
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
import com.khve.feature_auth.presentation.SignInFragment
import com.khve.feature_meta.presentation.adapter.MetaListAdapter
import com.khve.ui.R
import com.khve.ui.databinding.FragmentMetaListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MetaListFragment : Fragment() {

    private val viewModel: MetaListViewModel by viewModels()
    private var _binding: FragmentMetaListBinding? = null
    private val binding: FragmentMetaListBinding
        get() = _binding ?: throw NullPointerException("FragmentMetaListBinding == null")

    private lateinit var metaListAdapter: MetaListAdapter

    private var retrievedUserState: com.khve.feature_auth.domain.entity.UserState? = null
    private var partySize: com.khve.feature_meta.domain.entity.PartySizeEnum? = null

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

    override fun onResume() {
        super.onResume()
        parseParams()
    }

    private fun parseParams() {
        arguments?.let {
            partySize = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(PARTY_SIZE, com.khve.feature_meta.domain.entity.PartySizeEnum::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.getParcelable<com.khve.feature_meta.domain.entity.PartySizeEnum>(PARTY_SIZE)
            }

            if (partySize == null)
                throw IllegalArgumentException("MetaListFragment received empty Party size")

            viewModel.getMetaCardList(
                partySize
                    ?: throw IllegalArgumentException("Can not get Meta card list, Party size is empty")
            )
        }
    }

    private fun isOnPaneMode(): Boolean {
        return binding.fcvMetaItemContainer != null
    }

    private fun buttonListeners() {
        binding.fabAddMetaItem.visibility = View.VISIBLE
        binding.fabAddMetaItem.isClickable = true
        binding.fabAddMetaItem.setOnClickListener {
            val currentUser = retrievedUserState

            if (currentUser is com.khve.feature_auth.domain.entity.UserState.NotAuthorized) {
                startSignInFragment()
            } else if (currentUser is com.khve.feature_auth.domain.entity.UserState.User &&
                currentUser.user.hasPermission(com.khve.feature_auth.domain.entity.Permission.ADD_META_ITEM)) {
                startAddMetaItemFragment()
            } else {
                Toast.makeText(requireContext(), "Forbidden", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startSignInFragment() {
        replaceFragment(SignInFragment.newInstance(), R.id.auth_container)
    }

    private fun startAddMetaItemFragment() {
        val currentPartySize = partySize
        if (currentPartySize != null) {
            if (isOnPaneMode()) {
                replaceFragment(
                    AddMetaItemFragment.newInstance(currentPartySize),
                    R.id.fcv_meta_item_container
                )
            } else {
                replaceFragment(
                    AddMetaItemFragment.newInstance(currentPartySize),
                    R.id.auth_container
                )
            }
        } else {
            throw IllegalArgumentException("Can not start AddMetaItemFragment, party size is null")
        }
    }

    private fun startMetaItemFragment(metaCardItem: com.khve.feature_meta.domain.entity.MetaCardItem) {
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
                    if (it is com.khve.feature_auth.domain.entity.UserState.User) {
                        retrievedUserState = it
                        buttonListeners()
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
                    when (it) {
                        com.khve.feature_meta.domain.entity.MetaCardListState.Initial -> {}

                        is com.khve.feature_meta.domain.entity.MetaCardListState.MetaCardList -> {
                            val sortedMetaList = it.metaCardList.sortedBy {
                                resources.getStringArray(R.array.tiers).indexOf(it.tier)
                            }
                            metaListAdapter.submitList(sortedMetaList)
                            loadMetaListInProgress(false)
                        }

                        is com.khve.feature_meta.domain.entity.MetaCardListState.Error -> {
                            loadMetaListInProgress(false)
                            Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT)
                                .show()
                        }

                        com.khve.feature_meta.domain.entity.MetaCardListState.Progress -> {
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
        const val PARTY_SIZE = "party_size"

        @JvmStatic
        fun newInstance(partySize: com.khve.feature_meta.domain.entity.PartySizeEnum) = MetaListFragment().apply {
            arguments = Bundle().apply {
                putParcelable(PARTY_SIZE, partySize)
            }
        }
    }
}