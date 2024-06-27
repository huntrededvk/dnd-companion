package com.khve.dndcompanion.presentation.meta.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.khve.dndcompanion.domain.meta.entity.PartySizeEnum
import com.khve.dndcompanion.presentation.meta.MetaListFragment

class StateAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        return MetaListFragment.newInstance(PartySizeEnum.entries[position])
    }

    override fun getItemCount(): Int {
        return PartySizeEnum.entries.size
    }
}