package com.khve.dndcompanion.presentation.meta.adapter

import androidx.recyclerview.widget.DiffUtil
import com.khve.dndcompanion.domain.meta.entity.MetaItem

class MetaItemDiffCallback: DiffUtil.ItemCallback<MetaItem>() {
    override fun areItemsTheSame(oldItem: MetaItem, newItem: MetaItem): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: MetaItem, newItem: MetaItem): Boolean {
        return oldItem == newItem
    }
}