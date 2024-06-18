package com.khve.dndcompanion.presentation.meta

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.khve.dndcompanion.R
import com.khve.dndcompanion.data.meta.model.MetaItemDto
import com.khve.dndcompanion.databinding.FragmentAddMetaItemBinding
import com.khve.dndcompanion.domain.dnd.entity.DndArmor
import com.khve.dndcompanion.domain.dnd.entity.DndContentState
import com.khve.dndcompanion.domain.dnd.entity.DndItem
import com.khve.dndcompanion.domain.meta.entity.MetaItemState
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
    ): View {
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
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.add_meta_item_dropdown_item, tiers)
        binding.acttTierDropdownMenu.setAdapter(arrayAdapter)
    }

    private fun setAdapter(actt: AutoCompleteTextView, value: List<DndItem>) {
        actt.setAdapter(
            ArrayAdapter(
                requireContext(),
                R.layout.add_meta_item_dropdown_item,
                value.map { it.name }
            )
        )
    }

    private fun observerViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.dndContentState.collect {
                    if (it is DndContentState.Content) {
                        val content = it.content
                        val weapon = content.weapon
                        val rings = content.rings
                        val classes: List<DndItem> = content.classes.map { DndItem(it.name) }
                        setAdapter(binding.acttChestDropdownMenu, content.armor.chest)
                        setAdapter(binding.acttHeadDropdownMenu, content.armor.head)
                        setAdapter(binding.acttGlovesDropdownMenu, content.armor.gloves)
                        setAdapter(binding.acttLegDropdownMenu, content.armor.legs)
                        setAdapter(binding.acttFootDropdownMenu, content.armor.foot)
                        setAdapter(binding.acttBackDropdownMenu, content.armor.back)
                        setAdapter(binding.acttPrimaryWeaponLeftDropdownMenu, weapon)
                        setAdapter(binding.acttPrimaryWeaponRightDropdownMenu, weapon)
                        setAdapter(binding.acttSecondaryWeaponLeftDropdownMenu, weapon)
                        setAdapter(binding.acttSecondaryWeaponRightDropdownMenu, weapon)
                        setAdapter(binding.acttNecklessDropdownMenu, content.pendants)
                        setAdapter(binding.acttRignLeftDropdownMenu, rings)
                        setAdapter(binding.acttRingRightDropdownMenu, rings)
                        setAdapter(binding.acttClassDropdownMenu, classes)
                    }
                }
            }
        }

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