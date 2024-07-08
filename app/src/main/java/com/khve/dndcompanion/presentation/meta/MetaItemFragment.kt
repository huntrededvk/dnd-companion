package com.khve.dndcompanion.presentation.meta

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.bumptech.glide.Glide
import com.khve.dndcompanion.databinding.FragmentMetaItemBinding
import com.khve.dndcompanion.domain.meta.entity.MetaCardItem
import com.khve.dndcompanion.domain.meta.entity.MetaItem
import com.khve.dndcompanion.domain.meta.entity.MetaItemState
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MetaItemFragment : Fragment() {

    private val viewModel: MetaItemViewModel by viewModels()
    private var _binding: FragmentMetaItemBinding? = null
    private val binding: FragmentMetaItemBinding
        get() = _binding ?: throw NullPointerException("FragmentMetaItemBinding == null")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        parseParams()
        observeViewModel()
    }

    private fun setUpYoutubePlayer(youtubeVideoId: String?) {
        if (!youtubeVideoId.isNullOrEmpty()) {
            binding.llYoutubePlayer.visibility = View.VISIBLE
            val youTubePlayer = binding.youtubePlayerView
            lifecycle.addObserver(youTubePlayer)
            val youTubePlayerListener = object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    super.onReady(youTubePlayer)
                    youTubePlayer.cueVideo(youtubeVideoId, 0F)
                    youTubePlayer.removeListener(this)
                }}
            youTubePlayer.addYouTubePlayerListener(youTubePlayerListener)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.metaItemState.collect {
                    when(it) {
                        MetaItemState.Initial -> loadMetaItemInProgress(false)
                        is MetaItemState.Error -> {
                            loadMetaItemInProgress(false)
                            Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                        }
                        is MetaItemState.MetaItem -> {
                            val metaItem = it.metaItem
                            setupViews(metaItem)
                            buttonListeners(metaItem)
                            loadMetaItemInProgress(false)
                            setUpYoutubePlayer(metaItem.youtubeVideoId)
                        }
                        MetaItemState.Progress -> loadMetaItemInProgress(true)
                        MetaItemState.Success -> {
                            loadMetaItemInProgress(false)
                            requireActivity().supportFragmentManager.popBackStack(
                                MetaListTabFragment.BACKSTACK_NAME,
                                0
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadMetaItemInProgress(isInProgress: Boolean) {
        if (isInProgress) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    private fun buttonListeners(metaItem: MetaItem) {
        binding.btnDelete.setOnClickListener {
            viewModel.deleteMetaItem(metaItem)
        }

        binding.btnEdit.setOnClickListener {
            // TODO
        }
    }

    private fun setupViews(metaItem: MetaItem) {
        metaItem.let { item ->
            with(binding) {

                // Load class preview image
                context?.let {
                    Glide.with(it).load(item.dndClass[MetaItem.PREVIEW_IMAGE]).into(ivClassPreview)
                }

                // Set text values
                tvTier.text = item.tier
                tvTeamSize.text = item.partySize?.name
                tvClass.text = item.dndClass[MetaItem.NAME]
                tvTitle.text = item.title
                tvAuthor.text = item.author[MetaItem.USERNAME]
                tvDescription.text = item.description
                tvLeftWeaponSlotOne.text = item.primaryWeaponSlotOne[MetaItem.NAME]
                tvLeftWeaponSlotTwo.text = item.primaryWeaponSlotTwo[MetaItem.NAME]
                tvRightWeaponSlotOne.text = item.secondaryWeaponSlotOne[MetaItem.NAME]
                tvRightWeaponSlotTwo.text = item.secondaryWeaponSlotTwo[MetaItem.NAME]
                tvArmorHead.text = item.headArmor[MetaItem.NAME]
                tvArmorBack.text = item.backArmor[MetaItem.NAME]
                tvArmorChest.text = item.chestArmor[MetaItem.NAME]
                tvArmorLegs.text = item.legsArmor[MetaItem.NAME]
                tvArmorFoot.text = item.footArmor[MetaItem.NAME]
                tvArmorGloves.text = item.glovesArmor[MetaItem.NAME]
                tvJewelryPendant.text = item.pendant[MetaItem.NAME]
                tvJewelryRingSlotOne.text = item.ringSlotOne[MetaItem.NAME]
                tvJewelryRingSlotTwo.text = item.ringSlotTwo[MetaItem.NAME]

                // Load images
                loadImage(ivLeftWeaponSlotOne, item.primaryWeaponSlotOne[MetaItem.PREVIEW_IMAGE])
                loadImage(ivLeftWeaponSlotTwo, item.primaryWeaponSlotTwo[MetaItem.PREVIEW_IMAGE])
                loadImage(ivRightWeaponSlotOne, item.secondaryWeaponSlotOne[MetaItem.PREVIEW_IMAGE])
                loadImage(ivRightWeaponSlotTwo, item.secondaryWeaponSlotTwo[MetaItem.PREVIEW_IMAGE])
                loadImage(ivArmorHead, item.headArmor[MetaItem.PREVIEW_IMAGE])
                loadImage(ivArmorBack, item.backArmor[MetaItem.PREVIEW_IMAGE])
                loadImage(ivArmorChest, item.chestArmor[MetaItem.PREVIEW_IMAGE])
                loadImage(ivArmorLegs, item.legsArmor[MetaItem.PREVIEW_IMAGE])
                loadImage(ivArmorFoot, item.footArmor[MetaItem.PREVIEW_IMAGE])
                loadImage(ivArmorGloves, item.glovesArmor[MetaItem.PREVIEW_IMAGE])
                loadImage(ivJewelryPendant, item.pendant[MetaItem.PREVIEW_IMAGE])
                loadImage(ivJewelryRingSlotOne, item.ringSlotOne[MetaItem.PREVIEW_IMAGE])
                loadImage(ivJewelryRingSlotTwo, item.ringSlotTwo[MetaItem.PREVIEW_IMAGE])
            }
        }
    }

    private fun loadImage(imageView: ImageView, previewImage: String?) {
        context?.let { Glide.with(it).load(previewImage).into(imageView) }
    }

    private fun parseParams() {
        arguments?.let {
            val metaCardItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(META_CARD_ITEM, MetaCardItem::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.getParcelable<MetaCardItem>(META_CARD_ITEM)
            }

            if (metaCardItem == null)
                throw IllegalArgumentException("MetaItemFragment received empty Meta card item")
            else if (metaCardItem.partySize == null)
                throw IllegalArgumentException("MetaCardItem has null party size in MetaItemFragment")

            viewModel.getMetaItem(metaCardItem.uid, metaCardItem.partySize)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMetaItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        private const val META_CARD_ITEM = "meta_card_item"

        @JvmStatic
        fun newInstance(metaCardItem: MetaCardItem) =
            MetaItemFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(META_CARD_ITEM, metaCardItem)
                }
            }
    }
}
