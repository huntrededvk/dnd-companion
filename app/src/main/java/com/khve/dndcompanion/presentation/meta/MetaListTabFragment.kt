package com.khve.dndcompanion.presentation.meta

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.khve.dndcompanion.databinding.FragmentMetaListTabBinding
import com.khve.dndcompanion.domain.meta.entity.PartySizeEnum
import com.khve.dndcompanion.presentation.meta.adapter.StateAdapter

class MetaListTabFragment : Fragment() {

    private var _binding: FragmentMetaListTabBinding? = null
    private val binding: FragmentMetaListTabBinding
        get() = _binding ?: throw NullPointerException("FragmentMetaListTabBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMetaListTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val tabLayout = binding.tlMetaList
        val viewPager2 = binding.vp2MetaList
        viewPager2.adapter = StateAdapter(
            requireActivity().supportFragmentManager,
            requireActivity().lifecycle
        )
        TabLayoutMediator(tabLayout, viewPager2
        ) {
          tab, position -> tab.text = PartySizeEnum.entries[position].name
        }.attach()
    }



    companion object {
        const val BACKSTACK_NAME = "meta_list_tab_fragment"
        @JvmStatic
        fun newInstance() = MetaListTabFragment()
    }
}