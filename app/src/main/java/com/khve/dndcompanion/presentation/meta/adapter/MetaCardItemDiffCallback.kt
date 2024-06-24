package com.khve.dndcompanion.presentation.meta.adapter

import androidx.recyclerview.widget.DiffUtil
import com.khve.dndcompanion.domain.meta.entity.MetaCardItem

class MetaCardItemDiffCallback : DiffUtil.ItemCallback<MetaCardItem>() {
    override fun areItemsTheSame(oldItem: MetaCardItem, newItem: MetaCardItem): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: MetaCardItem, newItem: MetaCardItem): Boolean {
        return oldItem == newItem
    }
}
