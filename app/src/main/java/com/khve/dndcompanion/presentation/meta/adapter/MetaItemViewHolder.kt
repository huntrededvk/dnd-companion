package com.khve.dndcompanion.presentation.meta.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.khve.dndcompanion.R

class MetaItemViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    val cvMetaItem = view.findViewById<CardView>(R.id.cv_meta_item)
    val ivCharacterPreview = view.findViewById<ImageView>(R.id.iv_character_preview)
    val tvTitle = view.findViewById<TextView>(R.id.tv_title)
    val tvAuthor = view.findViewById<TextView>(R.id.tv_author)
    val tvTier = view.findViewById<TextView>(R.id.tv_tier)
}