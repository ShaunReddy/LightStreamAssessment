package com.example.shaunreddy.lightstreamassignment.location.usecase

import com.example.shaunreddy.lightstreamassignment.domain.util.*
import com.example.shaunreddy.lightstreamassignment.location.api.modal.RickAndMortyLocation
import com.example.shaunreddy.lightstreamassignment.location.repository.RickAndMortyLocationLocalRepository
import com.example.shaunreddy.lightstreamassignment.location.repository.RickAndMortyLocationRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

class RickAndMortyLocationUseCase(
    private val repository: RickAndMortyLocationRepository,
    private val locationLocalRepository: RickAndMortyLocationLocalRepository,
    private val scope: CoroutineScope
) {
    suspend fun execute(): StateFlow<AsyncOperation<List<RickAndMortyLocation>>> {
        return flow{
            emit(Loading(locationLocalRepository.getCachedLocations()))
            emit(
                handleRetrofitResponse(
                    request = {
                        repository.getLocations()
                    },
                    onSuccess = {
                        Success(locationLocalRepository.getCachedLocations())
                    },
                    onSaveToLocalDB = { locationResponse ->
                        locationResponse?.let {
                            locationLocalRepository.insertLocations(it.toRickAndMortyLocation())
                        }
                    },
                    onFailure = {
                        Failure(locationLocalRepository.getCachedLocations())
                    }
                )
            )
        }.stateIn(scope)
    }
}