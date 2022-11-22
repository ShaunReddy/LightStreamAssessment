package com.example.shaunreddy.lightstreamassignment.characters.view

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
import com.example.shaunreddy.lightstreamassignment.characters.view.adapter.RickAndMortyCharacterOverviewAdapter
import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Character
import com.example.shaunreddy.lightstreamassignment.characters.view.adapter.RickAndMortyCharacterOverviewAdapterHandler
import com.example.shaunreddy.lightstreamassignment.characters.viewModel.RickAndMortyCharactersViewModel
import com.example.shaunreddy.lightstreamassignment.databinding.RickandmortyBaseFragmentBinding
import com.example.shaunreddy.lightstreamassignment.domain.util.AsyncOperation
import com.example.shaunreddy.lightstreamassignment.domain.util.Failure
import com.example.shaunreddy.lightstreamassignment.domain.util.Loading
import com.example.shaunreddy.lightstreamassignment.domain.util.Success
import com.example.shaunreddy.lightstreamassignment.explore.view.RickAndMortyExploreFragmentDirections
import com.google.android.material.snackbar.Snackbar

class RickAndMortyCharactersFragment(viewModelFactory: ViewModelProvider.Factory? = null): Fragment(),
    RickAndMortyCharacterOverviewAdapterHandler {

    @VisibleForTesting
    lateinit var binding: RickandmortyBaseFragmentBinding
    private val viewModel: RickAndMortyCharactersViewModel by viewModels {
        viewModelFactory ?: defaultViewModelProviderFactory
    }

    private lateinit var adapter: RickAndMortyCharacterOverviewAdapter

    @VisibleForTesting
    lateinit var snackbar: Snackbar


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
        viewModel.charactersLiveData.observe(viewLifecycleOwner, ::observeCharactersLiveData)
        adapter = RickAndMortyCharacterOverviewAdapter(requireContext(), this)
        binding.rickandmortyRv.adapter = adapter
        snackbar = Snackbar.make(requireActivity().findViewById(android.R.id.content), "", Snackbar.LENGTH_SHORT)
    }


    private fun observeCharactersLiveData(asyncOperation: AsyncOperation<List<Character>>?) {
        when(asyncOperation) {
            is Loading -> {
                snackbar.apply {
                    setText(getString(R.string.rickandmorty_fetch_loading))
                    duration = Snackbar.LENGTH_INDEFINITE
                }.show()
                adapter.addList(asyncOperation.cachedResponse)
            }
            is Success -> {
                snackbar.dismiss()
                adapter.addList(asyncOperation.response)
            }
            is Failure -> {
                snackbar.apply {
                    setText(getString(R.string.rickandmorty_failed_to_fetch_data))
                    duration = Snackbar.LENGTH_INDEFINITE
                    setAction(requireContext().getText(R.string.rickandmorty_retry)) {
                        viewModel.getCharacters()
                    }
                }.show()
                adapter.addList(asyncOperation.cachedResponse)
            }
            else -> {}
        }
    }

    override fun onCharacterSelected(character: Character) {
        findNavController().navigate(RickAndMortyExploreFragmentDirections.rickandmortyCharacterOverviewDetails(character))
    }

}