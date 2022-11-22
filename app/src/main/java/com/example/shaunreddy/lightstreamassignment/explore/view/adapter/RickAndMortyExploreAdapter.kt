package com.example.shaunreddy.lightstreamassignment.explore.view.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.shaunreddy.lightstreamassignment.R
import com.example.shaunreddy.lightstreamassignment.characters.view.RickAndMortyCharactersFragment
import com.example.shaunreddy.lightstreamassignment.location.view.RickAndMortyLocationsFragment

class RickAndMortyExploreAdapter(
    private val fragment: Fragment
): FragmentStateAdapter(fragment) {

    companion object {
        const val TAB_COUNT = 2
    }

    override fun getItemCount(): Int = TAB_COUNT

    override fun createFragment(position: Int): Fragment {
        return if (position == 0){
            RickAndMortyCharactersFragment()
        } else {
            RickAndMortyLocationsFragment()
        }
    }

    fun getTabTitle(position: Int) = when(position) {
        0 -> fragment.getString(R.string.rickandmorty_characters_tab_title)
        else -> fragment.getString(R.string.rickandmorty_locations_tab_title)
    }
}