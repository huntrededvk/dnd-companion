package com.khve.dndcompanion.presentation.meta

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.khve.dndcompanion.R
import com.khve.dndcompanion.data.meta.model.MetaItemDto
import com.khve.dndcompanion.databinding.FragmentAddMetaItemBinding
import com.khve.dndcompanion.domain.auth.entity.UserState
import com.khve.dndcompanion.domain.auth.enum.Permission
import com.khve.dndcompanion.domain.auth.enum.UserRole
import com.khve.dndcompanion.domain.meta.entity.MetaItemState
import com.khve.dndcompanion.domain.meta.enum.Tier
import com.khve.dndcompanion.presentation.CompanionApplication
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddMetaItemFragment : Fragment() {

    @Inject
    lateinit var viewModel: AddMetaItemViewModel
    private var _binding: FragmentAddMetaItemBinding? = null
    private val binding: FragmentAddMetaItemBinding
        get() = _binding ?: throw RuntimeException("FragmentAddMetaItemFragment == null")

    private val component by lazy {
        (requireActivity().application as CompanionApplication).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onResume() {
        super.onResume()
        initDropDownMenu()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddMetaItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observerViewModel()
        buttonListener()
    }

    private fun initDropDownMenu() {
        val tiers = resources.getStringArray(R.array.tiers)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.add_meta_item_dropdown_item, tiers)
        binding.acttTierDropdownMenu.setAdapter(arrayAdapter)
    }

    private fun observerViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.metaItem.collect {
                    if (it is MetaItemState.Success) {
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                    if (it is MetaItemState.Error) {
                        Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun buttonListener() {
        binding.btnAddMetaItem.setOnClickListener {
            viewModel.addMetaItem(
                MetaItemDto(
                    title = binding.etTitle.text.toString().trim(),
                    description = binding.etDescription.text.toString().trim(),
                    tier = binding.acttTierDropdownMenu.text.toString().trim()
                )
            )
        }
    }

    companion object {
        const val BACKSTACK_NAME = "add_meta_item_fragment"
        @JvmStatic
        fun newInstance() = AddMetaItemFragment()
    }
}