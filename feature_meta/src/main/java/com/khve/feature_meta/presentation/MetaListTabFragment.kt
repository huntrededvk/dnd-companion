package com.khve.feature_meta.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.khve.feature_meta.presentation.adapter.StateAdapter
import com.khve.ui.databinding.FragmentMetaListTabBinding

class MetaListTabFragment : Fragment() {

    private var _binding: FragmentMetaListTabBinding? = null
    private val binding: FragmentMetaListTabBinding
        get() = _binding ?: throw NullPointerException("FragmentMetaListTabBinding == null")

    private lateinit var backPressedCallback: OnBackPressedCallback

    private fun setupBackPressed() {
        backPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finish()
            }
        }
        activity?.onBackPressedDispatcher?.addCallback(this, backPressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMetaListTabBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupBackPressed()
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
          tab, position -> tab.text = com.khve.feature_meta.domain.entity.PartySizeEnum.entries[position].name
        }.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        backPressedCallback.remove()
    }

    companion object {
        const val BACKSTACK_NAME = "meta_list_tab_fragment"
        @JvmStatic
        fun newInstance() = MetaListTabFragment()
    }
}