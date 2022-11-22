package com.example.shaunreddy.lightstreamassignment.characters.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.VisibleForTesting
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.shaunreddy.lightstreamassignment.R
import com.example.shaunreddy.lightstreamassignment.databinding.RickandmortyCharacterDetailsFragmentBinding

class RickAndMortyCharacterDetailsFragment: Fragment() {

    @VisibleForTesting
    lateinit var binding: RickandmortyCharacterDetailsFragmentBinding
    private val args: RickAndMortyCharacterDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RickandmortyCharacterDetailsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(args.rickAndMortyCharacterDetails) {
            binding.rickandmortyCharacterDetailsName.text = getString(R.string.rickandmorty_characters_name).format(name)
            binding.rickandmortyCharacterDetailsStatus.text = getString(R.string.rickandmorty_characters_status).format(status)
            binding.rickandmortyCharacterDetailsOrigin.text = getString(R.string.rickandmorty_characters_origin).format(origin.name)
            binding.rickandmortyCharacterDetailsSpecies.text = getString(R.string.rickandmorty_characters_species).format(species)
            binding.rickandmortyCharacterDetailsLastKnowLocation.text = getString(R.string.rickandmorty_characters_name).format(location.name)
            binding.rickandmortyCharacterDetailsGender.text = getString(R.string.rickandmorty_characters_gender).format(gender)
            Glide.with(this@RickAndMortyCharacterDetailsFragment)
                .load(image)
                .into(binding.rickandmortyCharacterDetailsImg)
        }
    }
}