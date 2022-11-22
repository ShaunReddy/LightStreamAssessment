package com.example.shaunreddy.lightstreamassignment.characters.repository

import com.example.shaunreddy.lightstreamassignment.characters.api.localStorage.RickAndMortyCharactersDao
import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Character

class RickAndMortyCharactersLocalRepositoryImpl(private val dao: RickAndMortyCharactersDao) : RickAndMortyCharactersLocalRepository{
    override suspend fun getCachedCharacters(): List<Character> = dao.getCharacters()

    override suspend fun insertCharacters(characters: List<Character>) {
        dao.insertAll(characters)
    }

}