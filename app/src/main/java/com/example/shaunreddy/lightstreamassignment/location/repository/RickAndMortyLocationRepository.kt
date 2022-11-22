package com.example.shaunreddy.lightstreamassignment.location.repository

import com.example.shaunreddy.lightstreamassignment.location.api.modal.RickAndMortyLocationsResponse
import retrofit2.Response

interface RickAndMortyLocationRepository {
    suspend fun getLocations():Response<RickAndMortyLocationsResponse>
}