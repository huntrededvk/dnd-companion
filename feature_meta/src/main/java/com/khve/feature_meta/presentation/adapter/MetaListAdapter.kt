package com.khve.feature_meta.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.khve.feature_meta.domain.entity.MetaCardItem
import com.khve.feature_meta.domain.entity.MetaItem
import com.khve.ui.R

class MetaListAdapter (
    private val context: Context
) : ListAdapter<MetaCardItem, MetaItemViewHolder>(
    MetaCardItemDiffCallback()
) {

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

        val metaItemTitle = metaCardItem.title
        val title = if (metaItemTitle.length > 20) {
            "${metaItemTitle.substring(0,16)}..."
        } else {
            metaItemTitle
        }
        holder.tvTitle.text = title
        holder.tvAuthor.text = metaCardItem.author[MetaItem.USERNAME]
        // Set karma values
        val karma = metaCardItem.likes.size - metaCardItem.dislikes.size
        val colorRes = if (karma >= 0) R.color.green else R.color.red
        holder.tvKarma.setTextColor(ContextCompat.getColor(context, colorRes))
        holder.tvKarma.text = karma.toString()
        Glide.with(context).load(metaCardItem.dndClass[MetaItem.PREVIEW_IMAGE])
            .into(holder.ivCharacterPreview)
    }
}
