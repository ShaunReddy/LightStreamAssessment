package com.example.shaunreddy.lightstreamassignment.characters.repository

import com.example.shaunreddy.lightstreamassignment.characters.api.modal.CharactersResponse
import retrofit2.Response
import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Character

interface RickAndMortyCharactersRepository {

    suspend fun getCharacters() : Response<CharactersResponse>
    suspend fun getCharactersByIds(ids: List<Int>) : Response<List<Character>>
}