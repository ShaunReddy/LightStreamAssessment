package com.example.shaunreddy.lightstreamassignment.location.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.shaunreddy.lightstreamassignment.R
import com.example.shaunreddy.lightstreamassignment.databinding.RickandmortyBaseFragmentBinding
import com.example.shaunreddy.lightstreamassignment.domain.util.AsyncOperation
import com.example.shaunreddy.lightstreamassignment.domain.util.Failure
import com.example.shaunreddy.lightstreamassignment.domain.util.Loading
import com.example.shaunreddy.lightstreamassignment.domain.util.Success
import com.example.shaunreddy.lightstreamassignment.explore.view.RickAndMortyExploreFragmentDirections
import com.example.shaunreddy.lightstreamassignment.location.api.modal.RickAndMortyLocation
import com.example.shaunreddy.lightstreamassignment.location.view.adapter.RickAndMortyLocationAdapterHandler
import com.example.shaunreddy.lightstreamassignment.location.view.adapter.RickAndMortyLocationsAdapter
import com.example.shaunreddy.lightstreamassignment.location.viewModel.RickAndMortyLocationsViewModel
import com.google.android.material.snackbar.Snackbar

class RickAndMortyLocationsFragment(
    private val viewModelFactory: ViewModelProvider.Factory ?= null
): Fragment(), RickAndMortyLocationAdapterHandler {

    @VisibleForTesting
    lateinit var binding: RickandmortyBaseFragmentBinding

    @VisibleForTesting
    lateinit var snackbar: Snackbar

     private val viewModel: RickAndMortyLocationsViewModel by viewModels {
        viewModelFactory ?: defaultViewModelProviderFactory
    }

    private lateinit var adapter: RickAndMortyLocationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RickandmortyBaseFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.locationsLiveData.observe(viewLifecycleOwner, ::observeLocationsLiveData)
        adapter = RickAndMortyLocationsAdapter(requireContext(), this)
        binding.rickandmortyRv.adapter = adapter
        snackbar = Snackbar.make(requireActivity().findViewById(android.R.id.content), "", Snackbar.LENGTH_SHORT)
    }

    private fun observeLocationsLiveData(operation: AsyncOperation<List<RickAndMortyLocation>>?) {
        when(operation) {
            is Loading -> {
                snackbar.apply {
                    setText(getString(R.string.rickandmorty_fetch_loading))
                    duration = Snackbar.LENGTH_INDEFINITE
                }.show()
                snackbar .show()
                adapter.updateItems(operation.cachedResponse)
            }
            is Success -> {
                snackbar.dismiss()
                adapter.updateItems(operation.response)
            }
            is Failure -> {
                snackbar.apply {
                    setText(getString(R.string.rickandmorty_failed_to_fetch_data))
                    duration = Snackbar.LENGTH_INDEFINITE
                    setAction(requireContext().getText(R.string.rickandmorty_retry)) {
                        viewModel.getLocations()
                    }
                }.show()
                adapter.updateItems(operation.cachedResponse)
            }
            else -> {}
        }
    }

    override fun onLocationSelected(location: RickAndMortyLocation) {
        findNavController().navigate(RickAndMortyExploreFragmentDirections.rickandmortyLocationsDetails(location))
    }
}