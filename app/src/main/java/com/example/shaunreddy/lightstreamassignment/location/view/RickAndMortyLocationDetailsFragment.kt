package com.example.shaunreddy.lightstreamassignment.location.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.shaunreddy.lightstreamassignment.R
import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Character
import com.example.shaunreddy.lightstreamassignment.characters.view.adapter.RickAndMortyCharacterOverviewAdapter
import com.example.shaunreddy.lightstreamassignment.characters.view.adapter.RickAndMortyCharacterOverviewAdapterHandler
import com.example.shaunreddy.lightstreamassignment.databinding.RickandmortyLocationDetailsFragmentBinding
import com.example.shaunreddy.lightstreamassignment.domain.util.AsyncOperation
import com.example.shaunreddy.lightstreamassignment.domain.util.Failure
import com.example.shaunreddy.lightstreamassignment.domain.util.Loading
import com.example.shaunreddy.lightstreamassignment.domain.util.Success
import com.example.shaunreddy.lightstreamassignment.location.viewModel.RickAndMortyLocationDetailsViewModel

class RickAndMortyLocationDetailsFragment(
    private val viewModelFactory: ViewModelProvider.Factory ?= null
): Fragment(), RickAndMortyCharacterOverviewAdapterHandler {

    @VisibleForTesting
    lateinit var binding: RickandmortyLocationDetailsFragmentBinding
    private val args: RickAndMortyLocationDetailsFragmentArgs by navArgs()

    private val viewModel: RickAndMortyLocationDetailsViewModel by viewModels {
        viewModelFactory ?: defaultViewModelProviderFactory
    }

    private lateinit var adapter: RickAndMortyCharacterOverviewAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RickandmortyLocationDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(args.rickandMortyLocationDetails) {
            binding.rickandmortyLocationDetailsName.text = getString(R.string.rickandmorty_location_name).format(name)
            binding.rickandmortyLocationDetailsType.text = getString(R.string.rickandmorty_location_type).format(type)
            binding.rickandmortyLocationDetailsDimension.text = getString(R.string.rickandmorty_location_dimension).format(dimension)
            binding.rickandmortyLocationDetailsResidents.text = getString(R.string.rickandmorty_location_details_residents).format(residents.size)
            binding.rickandmortyLocationDetailsResidents.setTextColor(requireContext().getColor(R.color.black))
            if(residents.isNotEmpty()){
                viewModel.getCharactersByIds(residents)
            }
        }
        adapter = RickAndMortyCharacterOverviewAdapter(requireContext(), this)
        binding.rickandmortyLocationDetailsResidentsRv.adapter = adapter
        viewModel.charactersLiveData.observe(viewLifecycleOwner, ::observeCharactersLiveData)
    }

    private fun observeCharactersLiveData(operation: AsyncOperation<List<Character>>?) {
        when(operation) {
            is Success -> {
                adapter.addList(operation.response)
                binding.rickandmortyLocationDetailsResidentsRv.isVisible = true
                binding.rickandmortyLocationDetailsResidentsMessage.isVisible = false
            }
            is Failure -> {
                binding.rickandmortyLocationDetailsResidentsRv.isVisible = false
                binding.rickandmortyLocationDetailsResidentsMessage.isVisible = true
                binding.rickandmortyLocationDetailsResidentsMessage.text = getString(R.string.rickandmorty_location_details_residents_failed)
            }
            is Loading -> {
                binding.rickandmortyLocationDetailsResidentsRv.isVisible = false
                binding.rickandmortyLocationDetailsResidentsMessage.isVisible = true
                binding.rickandmortyLocationDetailsResidentsMessage.text = getString(R.string.rickandmorty_location_details_residents_loading)
            }
            else -> {

            }
         }
    }

    override fun onCharacterSelected(character: Character) {
        findNavController().navigate(RickAndMortyLocationDetailsFragmentDirections.rickandmortyLocationsDetailsCharacterDetails(character))
    }
}