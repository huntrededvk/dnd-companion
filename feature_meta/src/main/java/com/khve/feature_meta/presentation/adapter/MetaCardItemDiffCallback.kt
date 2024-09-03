package com.khve.feature_meta.presentation.adapter

import androidx.recyclerview.widget.DiffUtil
import com.khve.feature_meta.domain.entity.MetaCardItem

class MetaCardItemDiffCallback : DiffUtil.ItemCallback<MetaCardItem>() {
    override fun areItemsTheSame(oldItem: MetaCardItem, newItem: MetaCardItem): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: MetaCardItem, newItem: MetaCardItem): Boolean {
        return oldItem == newItem
    }
}
