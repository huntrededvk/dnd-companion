package com.khve.feature_meta.presentation

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.khve.feature_dnd.domain.entity.DndContent
import com.khve.feature_dnd.domain.entity.DndContentState
import com.khve.feature_dnd.domain.entity.DndItem
import com.khve.ui.R
import com.khve.ui.databinding.FragmentAddMetaItemBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddMetaItemFragment : Fragment() {

    private val viewModel: AddMetaItemViewModel by viewModels()
    private var _binding: FragmentAddMetaItemBinding? = null
    private val binding: FragmentAddMetaItemBinding
        get() = _binding ?: throw NullPointerException("FragmentAddMetaItemBinding == null")

    private var partySize: com.khve.feature_meta.domain.entity.PartySizeEnum? = null

    override fun onAttach(context: Context) {
        parseParams()
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
    }

    private fun initDropDownMenu() {
        val tiers = resources.getStringArray(R.array.tiers)
        val arrayAdapter =
            ArrayAdapter(requireContext(), R.layout.add_meta_item_dropdown_item, tiers)
        binding.acttTierDropdownMenu.setAdapter(arrayAdapter)
    }

    private fun setAdapter(actt: AutoCompleteTextView, items: List<DndItem>) {
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.add_meta_item_dropdown_item,
            items.map(DndItem::name)
        )
        actt.setAdapter(adapter)
    }

    private fun observerViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.dndContentState.collect { state ->
                    when(state) {
                        is DndContentState.Content -> {
                            inProgress(false)
                            buttonListener(state.content)
                            with(state.content) {
                                setAdapter(binding.acttChestDropdownMenu, armor.chest)
                                setAdapter(binding.acttHeadDropdownMenu, armor.head)
                                setAdapter(binding.acttGlovesDropdownMenu, armor.gloves)
                                setAdapter(binding.acttLegDropdownMenu, armor.legs)
                                setAdapter(binding.acttFootDropdownMenu, armor.foot)
                                setAdapter(binding.acttBackDropdownMenu, armor.back)
                                setAdapter(binding.acttNecklessDropdownMenu, pendants)
                                setAdapter(
                                    binding.acttClassDropdownMenu,
                                    classes.map { DndItem(it.name) })
                                listOf(
                                    binding.acttPrimaryWeaponLeftDropdownMenu,
                                    binding.acttPrimaryWeaponRightDropdownMenu,
                                    binding.acttSecondaryWeaponLeftDropdownMenu,
                                    binding.acttSecondaryWeaponRightDropdownMenu
                                ).forEach { setAdapter(it, weapon) }
                                listOf(
                                    binding.acttRignLeftDropdownMenu,
                                    binding.acttRingRightDropdownMenu
                                ).forEach { setAdapter(it, rings) }
                            }
                            inProgress(false)
                        }
                        DndContentState.Initial -> inProgress(false)
                        DndContentState.Progress -> inProgress(true)
                    }
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.metaItem.collect {
                    when(it) {
                        com.khve.feature_meta.domain.entity.MetaItemState.Initial -> inProgress(false)
                        is com.khve.feature_meta.domain.entity.MetaItemState.MetaItem -> {}
                        com.khve.feature_meta.domain.entity.MetaItemState.Progress -> inProgress(true)
                        com.khve.feature_meta.domain.entity.MetaItemState.Success -> {
                            inProgress(false)
                            requireActivity().supportFragmentManager.popBackStack()
                        }
                        is com.khve.feature_meta.domain.entity.MetaItemState.Error -> {
                            inProgress(false)
                            Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
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
                throw IllegalArgumentException("AddMetaItemFragment received empty Party size")
        }
    }

    private fun buttonListener(content: DndContent) {
        binding.btnAddMetaItem.setOnClickListener {
            val selectedClass = binding.acttClassDropdownMenu.text.toString().trim()
            viewModel.addMetaItem(
                com.khve.feature_meta.domain.entity.MetaItem(
                    title = binding.etTitle.text.toString().trim(),
                    description = binding.etDescription.text.toString().trim(),
                    partySize = partySize,
                    tier = binding.acttTierDropdownMenu.text.toString().trim(),
                    youtubeVideoId = binding.etYoutubeVideoId.text.toString().trim(),
                    dndClass = mapOf(
                        com.khve.feature_meta.domain.entity.MetaItem.NAME to selectedClass,
                        com.khve.feature_meta.domain.entity.MetaItem.PREVIEW_IMAGE to
                                content.classes
                                    .find { it.name == selectedClass }?.previewImage.orEmpty()
                    ),
                    primaryWeaponSlotOne = getDndItem(
                        binding.acttPrimaryWeaponLeftDropdownMenu,
                        content.weapon
                    ),
                    primaryWeaponSlotTwo = getDndItem(
                        binding.acttPrimaryWeaponRightDropdownMenu,
                        content.weapon
                    ),
                    secondaryWeaponSlotOne = getDndItem(
                        binding.acttSecondaryWeaponLeftDropdownMenu,
                        content.weapon
                    ),
                    secondaryWeaponSlotTwo = getDndItem(
                        binding.acttSecondaryWeaponRightDropdownMenu,
                        content.weapon
                    ),
                    headArmor = getDndItem(binding.acttHeadDropdownMenu, content.armor.head),
                    chestArmor = getDndItem(binding.acttChestDropdownMenu, content.armor.chest),
                    legsArmor = getDndItem(binding.acttLegDropdownMenu, content.armor.legs),
                    footArmor = getDndItem(binding.acttFootDropdownMenu, content.armor.foot),
                    glovesArmor = getDndItem(binding.acttGlovesDropdownMenu, content.armor.gloves),
                    backArmor = getDndItem(binding.acttBackDropdownMenu, content.armor.back),
                    pendant = getDndItem(binding.acttNecklessDropdownMenu, content.pendants),
                    ringSlotOne = getDndItem(binding.acttRignLeftDropdownMenu, content.rings),
                    ringSlotTwo = getDndItem(binding.acttRingRightDropdownMenu, content.rings)
                )
            )
        }
    }

    private fun inProgress(isInProgress: Boolean) {
        if (isInProgress) {
            binding.btnAddMetaItem.isClickable = false
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.btnAddMetaItem.isClickable = true
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun getDndItem(actt: AutoCompleteTextView, items: List<DndItem>): Map<String, String> {
        val selectedItemName = actt.text.toString().trim()
        val previewImage = items.find { it.name == selectedItemName }?.previewImage.orEmpty()
        return mapOf(com.khve.feature_meta.domain.entity.MetaItem.NAME to selectedItemName, com.khve.feature_meta.domain.entity.MetaItem.PREVIEW_IMAGE to previewImage)
    }

    companion object {
        private const val PARTY_SIZE = "party_size"
        @JvmStatic
        fun newInstance(partySize: com.khve.feature_meta.domain.entity.PartySizeEnum) = AddMetaItemFragment().apply {
            arguments = Bundle().apply {
                putParcelable(PARTY_SIZE, partySize)
            }
        }
    }
}