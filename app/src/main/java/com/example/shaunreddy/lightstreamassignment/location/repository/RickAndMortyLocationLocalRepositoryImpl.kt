package com.example.shaunreddy.lightstreamassignment.location.repository

import com.example.shaunreddy.lightstreamassignment.location.api.localStorage.RickAndMortyLocationDao
import com.example.shaunreddy.lightstreamassignment.location.api.modal.RickAndMortyLocation

class RickAndMortyLocationLocalRepositoryImpl(
    private val dao: RickAndMortyLocationDao
): RickAndMortyLocationLocalRepository {
    override suspend fun getCachedLocations(): List<RickAndMortyLocation> {
        return dao.getLocations()
    }

    override suspend fun insertLocations(locations: List<RickAndMortyLocation>) {
        dao.insertAll(locations)
    }
}