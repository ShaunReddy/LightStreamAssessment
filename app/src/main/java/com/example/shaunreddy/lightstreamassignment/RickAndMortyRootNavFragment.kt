package com.example.shaunreddy.lightstreamassignment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.example.shaunreddy.lightstreamassignment.databinding.RickandmortyRootNavFragmentBinding

class RickAndMortyRootNavFragment: NavHostFragment() {

    private lateinit var binding: RickandmortyRootNavFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RickandmortyRootNavFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(requireActivity() as AppCompatActivity) {
            getNavHostController().addOnDestinationChangedListener { _, _, _ ->
                supportActionBar?.let { actionBar ->
                    actionBar.setDisplayHomeAsUpEnabled(isNavBackSupported())
                }
            }
        }

    }

    private fun isNavBackSupported() = getNavHostController().currentDestination?.id != R.id.rickandmorty_explore_fragment

    private fun getNavHostController() = (childFragmentManager.findFragmentById(R.id.rickandmorty_root_nav_host_fragment)as NavHostFragment).navController



}