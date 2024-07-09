package com.khve.feature_meta.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.khve.ui.R

class MetaListAdapter (
    private val context: Context
) : ListAdapter<com.khve.feature_meta.domain.entity.MetaCardItem, MetaItemViewHolder>(
    MetaCardItemDiffCallback()
) {

    var onMetaItemClickListener: ((com.khve.feature_meta.domain.entity.MetaCardItem) -> Unit)? = null

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

        val metaItemTitle = metaCardItem.title
        val title = if (metaItemTitle.length > 20) {
            "${metaItemTitle.substring(0,16)}..."
        } else {
            metaItemTitle
        }
        holder.tvTitle.text = title
        holder.tvAuthor.text = metaCardItem.author[com.khve.feature_meta.domain.entity.MetaItem.USERNAME]
        holder.tvTier.text = metaCardItem.tier
        Glide.with(context).load(metaCardItem.dndClass[com.khve.feature_meta.domain.entity.MetaItem.PREVIEW_IMAGE])
            .into(holder.ivCharacterPreview)
    }
}
