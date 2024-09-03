package com.khve.feature_meta.presentation.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.khve.feature_meta.domain.entity.PartySizeEnum
import com.khve.feature_meta.presentation.MetaListFragment

class StateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return MetaListFragment.newInstance(PartySizeEnum.entries[position])
    }

    override fun getItemCount(): Int {
        return PartySizeEnum.entries.size
    }
}