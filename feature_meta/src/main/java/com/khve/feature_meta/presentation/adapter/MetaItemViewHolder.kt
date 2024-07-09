package com.khve.feature_meta.presentation.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.khve.ui.R

class MetaItemViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    val cvMetaItem: CardView = view.findViewById(R.id.cv_meta_item)
    val ivCharacterPreview: ImageView = view.findViewById(R.id.iv_character_preview)
    val tvTitle: TextView = view.findViewById(R.id.tv_title)
    val tvAuthor: TextView = view.findViewById(R.id.tv_author)
    val tvTier: TextView = view.findViewById(R.id.tv_tier)
}