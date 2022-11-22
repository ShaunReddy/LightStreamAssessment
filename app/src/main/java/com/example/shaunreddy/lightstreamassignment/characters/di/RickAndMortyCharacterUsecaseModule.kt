package com.example.shaunreddy.lightstreamassignment.characters.di

import com.example.shaunreddy.lightstreamassignment.characters.usecase.RickAndMortyCharacterOverViewUseCase
import kotlinx.coroutines.CoroutineScope
import org.koin.dsl.module

val rickAndMortyCharactersUsecaseModule = module {
    single{ (scope: CoroutineScope) -> RickAndMortyCharacterOverViewUseCase(
        repository = get(),
        localRepository = get(),
        scope = scope
    ) }
}