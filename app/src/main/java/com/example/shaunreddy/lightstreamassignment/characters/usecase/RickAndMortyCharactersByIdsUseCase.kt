package com.example.shaunreddy.lightstreamassignment.characters.usecase

import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Character
import com.example.shaunreddy.lightstreamassignment.characters.repository.RickAndMortyCharactersRepository
import com.example.shaunreddy.lightstreamassignment.domain.util.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RickAndMortyCharactersByIdsUseCase(
    private val repository: RickAndMortyCharactersRepository
) {
    suspend fun execute(ids: List<Int>): Flow<AsyncOperation<List<Character>>> {

        return flow {
            emit(Loading(listOf()))
            try {
                val response = repository.getCharactersByIds(ids)
                when {
                    response.isSuccessful -> {
                        response.body()?.let {
                            val characters = it.sortedBy { character ->
                                character.name
                            }
                            emit(Success(characters))
                        }
                    }
                    else -> {
                        response.errorBody()?.let {
                            emit(Failure(listOf()))
                        }
                    }
                }
            } catch (e: Exception) {
                emit(Failure(listOf()))
            }
        }
    }
}