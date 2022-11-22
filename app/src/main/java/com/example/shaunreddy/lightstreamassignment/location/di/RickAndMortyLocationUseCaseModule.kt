package com.example.shaunreddy.lightstreamassignment.location.di

import com.example.shaunreddy.lightstreamassignment.characters.usecase.RickAndMortyCharactersByIdsUseCase
import com.example.shaunreddy.lightstreamassignment.location.usecase.RickAndMortyLocationUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.module

val rickAndMortyLocationUseCaseModule = module {
    single { (scope: CoroutineScope) -> RickAndMortyLocationUseCase(
        repository = get(),
        locationLocalRepository = get(),
        scope = scope
    ) }
    single { RickAndMortyCharactersByIdsUseCase(repository = get())}
}