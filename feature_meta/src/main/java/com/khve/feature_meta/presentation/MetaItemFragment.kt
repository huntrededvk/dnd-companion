package com.khve.feature_meta.presentation

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
import com.khve.ui.databinding.FragmentMetaItemBinding
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
                        com.khve.feature_meta.domain.entity.MetaItemState.Initial -> loadMetaItemInProgress(false)
                        is com.khve.feature_meta.domain.entity.MetaItemState.Error -> {
                            loadMetaItemInProgress(false)
                            Toast.makeText(requireContext(), it.errorMessage, Toast.LENGTH_SHORT).show()
                        }
                        is com.khve.feature_meta.domain.entity.MetaItemState.MetaItem -> {
                            val metaItem = it.metaItem
                            setupViews(metaItem)
                            buttonListeners(metaItem)
                            loadMetaItemInProgress(false)
                            setUpYoutubePlayer(metaItem.youtubeVideoId)
                        }
                        com.khve.feature_meta.domain.entity.MetaItemState.Progress -> loadMetaItemInProgress(true)
                        com.khve.feature_meta.domain.entity.MetaItemState.Success -> {
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

    private fun buttonListeners(metaItem: com.khve.feature_meta.domain.entity.MetaItem) {
        binding.btnDelete.setOnClickListener {
            viewModel.deleteMetaItem(metaItem)
        }

        binding.btnEdit.setOnClickListener {
            // TODO
        }
    }

    private fun setupViews(metaItem: com.khve.feature_meta.domain.entity.MetaItem) {
        metaItem.let { item ->
            with(binding) {

                // Load class preview image
                context?.let {
                    Glide.with(it).load(item.dndClass[com.khve.feature_meta.domain.entity.MetaItem.PREVIEW_IMAGE]).into(ivClassPreview)
                }

                // Set text values
                tvTier.text = item.tier
                tvTeamSize.text = item.partySize?.name
                tvClass.text = item.dndClass[com.khve.feature_meta.domain.entity.MetaItem.NAME]
                tvTitle.text = item.title
                tvAuthor.text = item.author[com.khve.feature_meta.domain.entity.MetaItem.USERNAME]
                tvDescription.text = item.description
                tvLeftWeaponSlotOne.text = item.primaryWeaponSlotOne[com.khve.feature_meta.domain.entity.MetaItem.NAME]
                tvLeftWeaponSlotTwo.text = item.primaryWeaponSlotTwo[com.khve.feature_meta.domain.entity.MetaItem.NAME]
                tvRightWeaponSlotOne.text = item.secondaryWeaponSlotOne[com.khve.feature_meta.domain.entity.MetaItem.NAME]
                tvRightWeaponSlotTwo.text = item.secondaryWeaponSlotTwo[com.khve.feature_meta.domain.entity.MetaItem.NAME]
                tvArmorHead.text = item.headArmor[com.khve.feature_meta.domain.entity.MetaItem.NAME]
                tvArmorBack.text = item.backArmor[com.khve.feature_meta.domain.entity.MetaItem.NAME]
                tvArmorChest.text = item.chestArmor[com.khve.feature_meta.domain.entity.MetaItem.NAME]
                tvArmorLegs.text = item.legsArmor[com.khve.feature_meta.domain.entity.MetaItem.NAME]
                tvArmorFoot.text = item.footArmor[com.khve.feature_meta.domain.entity.MetaItem.NAME]
                tvArmorGloves.text = item.glovesArmor[com.khve.feature_meta.domain.entity.MetaItem.NAME]
                tvJewelryPendant.text = item.pendant[com.khve.feature_meta.domain.entity.MetaItem.NAME]
                tvJewelryRingSlotOne.text = item.ringSlotOne[com.khve.feature_meta.domain.entity.MetaItem.NAME]
                tvJewelryRingSlotTwo.text = item.ringSlotTwo[com.khve.feature_meta.domain.entity.MetaItem.NAME]

                // Load images
                loadImage(ivLeftWeaponSlotOne, item.primaryWeaponSlotOne[com.khve.feature_meta.domain.entity.MetaItem.PREVIEW_IMAGE])
                loadImage(ivLeftWeaponSlotTwo, item.primaryWeaponSlotTwo[com.khve.feature_meta.domain.entity.MetaItem.PREVIEW_IMAGE])
                loadImage(ivRightWeaponSlotOne, item.secondaryWeaponSlotOne[com.khve.feature_meta.domain.entity.MetaItem.PREVIEW_IMAGE])
                loadImage(ivRightWeaponSlotTwo, item.secondaryWeaponSlotTwo[com.khve.feature_meta.domain.entity.MetaItem.PREVIEW_IMAGE])
                loadImage(ivArmorHead, item.headArmor[com.khve.feature_meta.domain.entity.MetaItem.PREVIEW_IMAGE])
                loadImage(ivArmorBack, item.backArmor[com.khve.feature_meta.domain.entity.MetaItem.PREVIEW_IMAGE])
                loadImage(ivArmorChest, item.chestArmor[com.khve.feature_meta.domain.entity.MetaItem.PREVIEW_IMAGE])
                loadImage(ivArmorLegs, item.legsArmor[com.khve.feature_meta.domain.entity.MetaItem.PREVIEW_IMAGE])
                loadImage(ivArmorFoot, item.footArmor[com.khve.feature_meta.domain.entity.MetaItem.PREVIEW_IMAGE])
                loadImage(ivArmorGloves, item.glovesArmor[com.khve.feature_meta.domain.entity.MetaItem.PREVIEW_IMAGE])
                loadImage(ivJewelryPendant, item.pendant[com.khve.feature_meta.domain.entity.MetaItem.PREVIEW_IMAGE])
                loadImage(ivJewelryRingSlotOne, item.ringSlotOne[com.khve.feature_meta.domain.entity.MetaItem.PREVIEW_IMAGE])
                loadImage(ivJewelryRingSlotTwo, item.ringSlotTwo[com.khve.feature_meta.domain.entity.MetaItem.PREVIEW_IMAGE])
            }
        }
    }

    private fun loadImage(imageView: ImageView, previewImage: String?) {
        context?.let { Glide.with(it).load(previewImage).into(imageView) }
    }

    private fun parseParams() {
        arguments?.let {
            val metaCardItem = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelable(META_CARD_ITEM, com.khve.feature_meta.domain.entity.MetaCardItem::class.java)
            } else {
                @Suppress("DEPRECATION")
                it.getParcelable<com.khve.feature_meta.domain.entity.MetaCardItem>(META_CARD_ITEM)
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
        fun newInstance(metaCardItem: com.khve.feature_meta.domain.entity.MetaCardItem) =
            MetaItemFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(META_CARD_ITEM, metaCardItem)
                }
            }
    }
}
