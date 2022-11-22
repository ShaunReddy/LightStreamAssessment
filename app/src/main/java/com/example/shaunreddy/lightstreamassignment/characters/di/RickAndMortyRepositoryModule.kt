package com.example.shaunreddy.lightstreamassignment.characters.view.di

import com.example.shaunreddy.lightstreamassignment.characters.repository.RickAndMortyCharacterRepositoryImpl
import com.example.shaunreddy.lightstreamassignment.characters.repository.RickAndMortyCharactersLocalRepository
import com.example.shaunreddy.lightstreamassignment.characters.repository.RickAndMortyCharactersLocalRepositoryImpl
import com.example.shaunreddy.lightstreamassignment.characters.repository.RickAndMortyCharactersRepository
import com.example.shaunreddy.lightstreamassignment.domain.RickAndMortyDatabase
import org.koin.dsl.module

val rickAndMortyRepositoryModule = module {
    single<RickAndMortyCharactersRepository> { RickAndMortyCharacterRepositoryImpl(get()) }
    single< RickAndMortyCharactersLocalRepository > { RickAndMortyCharactersLocalRepositoryImpl(dao = get<RickAndMortyDatabase>().getCharactersDao()) }
}