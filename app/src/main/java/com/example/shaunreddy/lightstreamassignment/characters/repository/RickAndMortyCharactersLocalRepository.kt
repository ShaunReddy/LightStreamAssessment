package com.example.shaunreddy.lightstreamassignment.characters.repository

import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Character

interface RickAndMortyCharactersLocalRepository {

    suspend fun getCachedCharacters(): List<Character>

    suspend fun insertCharacters(characters: List<Character>)
}