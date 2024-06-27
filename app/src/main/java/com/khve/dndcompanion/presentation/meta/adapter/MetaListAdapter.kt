package com.khve.dndcompanion.presentation.meta.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.khve.dndcompanion.R
import com.khve.dndcompanion.domain.meta.entity.MetaCardItem
import com.khve.dndcompanion.domain.meta.entity.MetaItem

class MetaListAdapter (
    private val context: Context
) : ListAdapter<MetaCardItem, MetaItemViewHolder>(MetaCardItemDiffCallback()) {

    var onMetaItemClickListener: ((MetaCardItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MetaItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meta_item, parent, false)
        return MetaItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: MetaItemViewHolder, position: Int) {
        val metaCardItem = getItem(position)
        holder.cvMetaItem.setOnClickListener {
            onMetaItemClickListener?.invoke(metaCardItem)
        }

        if (!metaCardItem.activated) {
            holder.cvMetaItem.background = AppCompatResources.getDrawable(context, R.color.deactivated)
        } else {
            holder.cvMetaItem.background = AppCompatResources.getDrawable(context, R.color.white)
        }

        holder.tvTitle.text = metaCardItem.title
        holder.tvAuthor.text = metaCardItem.author[MetaItem.USERNAME]
        holder.tvTier.text = metaCardItem.tier
        Glide.with(context).load(metaCardItem.dndClass[MetaItem.PREVIEW_IMAGE])
            .into(holder.ivCharacterPreview)
    }
}
