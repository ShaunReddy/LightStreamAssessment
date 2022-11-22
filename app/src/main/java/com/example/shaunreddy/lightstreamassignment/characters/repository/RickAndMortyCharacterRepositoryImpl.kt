package com.example.shaunreddy.lightstreamassignment.characters.repository

import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Character
import com.example.shaunreddy.lightstreamassignment.characters.api.modal.CharactersResponse
import com.example.shaunreddy.lightstreamassignment.domain.ServiceInterface
import retrofit2.Response

class RickAndMortyCharacterRepositoryImpl(
    private val serviceInterface: ServiceInterface
) : RickAndMortyCharactersRepository {

    override suspend fun getCharacters(): Response<CharactersResponse> {
        return serviceInterface.getCharacters()
    }

    override suspend fun getCharactersByIds(ids: List<Int>): Response<List<Character>> {
        return serviceInterface.getCharactersByIds(ids)
    }

}