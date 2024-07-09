package com.khve.feature_meta.presentation.adapter

import androidx.recyclerview.widget.DiffUtil

class MetaCardItemDiffCallback : DiffUtil.ItemCallback<com.khve.feature_meta.domain.entity.MetaCardItem>() {
    override fun areItemsTheSame(oldItem: com.khve.feature_meta.domain.entity.MetaCardItem, newItem: com.khve.feature_meta.domain.entity.MetaCardItem): Boolean {
        return oldItem.uid == newItem.uid
    }

    override fun areContentsTheSame(oldItem: com.khve.feature_meta.domain.entity.MetaCardItem, newItem: com.khve.feature_meta.domain.entity.MetaCardItem): Boolean {
        return oldItem == newItem
    }
}
