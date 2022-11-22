package com.example.shaunreddy.lightstreamassignment.location.repository

import com.example.shaunreddy.lightstreamassignment.domain.ServiceInterface
import com.example.shaunreddy.lightstreamassignment.location.api.modal.RickAndMortyLocationsResponse
import retrofit2.Response

class RickAndMortyLocationRepositoryImpl(
    private val serviceInterface: ServiceInterface
): RickAndMortyLocationRepository {

    override suspend fun getLocations(): Response<RickAndMortyLocationsResponse> {
        return serviceInterface.getLocations()
    }
}