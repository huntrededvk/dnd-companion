package com.khve.dndcompanion.presentation.meta.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ListAdapter
import com.khve.dndcompanion.R
import com.khve.dndcompanion.domain.meta.entity.MetaItem

class MetaListAdapter : ListAdapter<MetaItem, MetaItemViewHolder>(MetaItemDiffCallback()) {

    var onMetaItemClickListener: ((MetaItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetaItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meta_item, parent, false)
        return MetaItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MetaItemViewHolder, position: Int) {
        val metaItem = getItem(position)
        holder.cvMetaItem.setOnClickListener {
            onMetaItemClickListener?.invoke(metaItem)
        }

        holder.tvTitle.text = metaItem.title
        holder.tvAuthor.text = metaItem.authorUsername
        holder.tvTier.text = metaItem.tier
        holder.ivCharacterPreview.setBackgroundResource(R.mipmap.dnd_rogue)
    }
}