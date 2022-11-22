package com.example.shaunreddy.lightstreamassignment.explore.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.shaunreddy.lightstreamassignment.databinding.RickandmortyExloreFragmentBinding
import com.example.shaunreddy.lightstreamassignment.explore.view.adapter.RickAndMortyExploreAdapter
import com.google.android.material.tabs.TabLayoutMediator

class RickAndMortyExploreFragment: Fragment() {

    lateinit var binding: RickandmortyExloreFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RickandmortyExloreFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            val adapter = RickAndMortyExploreAdapter(this@RickAndMortyExploreFragment)
            rickandmortyViewPager.adapter = adapter
            TabLayoutMediator(rickandmortyTabLayout, rickandmortyViewPager) { tab, position ->
                tab.text = adapter.getTabTitle(position)
            }.attach()
        }
    }
}