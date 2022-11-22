package com.example.shaunreddy.lightstreamassignment.characters.usecase

import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Character
import com.example.shaunreddy.lightstreamassignment.characters.repository.RickAndMortyCharactersLocalRepository
import com.example.shaunreddy.lightstreamassignment.characters.repository.RickAndMortyCharactersRepository
import com.example.shaunreddy.lightstreamassignment.domain.util.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*


class RickAndMortyCharacterOverViewUseCase(
    private val repository: RickAndMortyCharactersRepository,
    private val localRepository: RickAndMortyCharactersLocalRepository,
    private val scope: CoroutineScope
){
    suspend fun execute(): StateFlow<AsyncOperation<List<Character>>> {
        return flow {
            emit(Loading(localRepository.getCachedCharacters()))
            emit(
                handleRetrofitResponse(
                    request = {
                        repository.getCharacters()
                    },
                    onSuccess = {
                        Success(localRepository.getCachedCharacters())
                    },
                    onSaveToLocalDB = {
                        it?.results?.let { results ->
                            localRepository.insertCharacters(results)
                        }
                    },
                    onFailure = {
                        Failure(localRepository.getCachedCharacters())
                    }
                )
            )
        }.stateIn(scope = scope)
    }
}