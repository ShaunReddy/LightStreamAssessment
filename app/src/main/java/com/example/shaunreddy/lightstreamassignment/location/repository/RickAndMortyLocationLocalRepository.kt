package com.example.shaunreddy.lightstreamassignment.location.repository

import com.example.shaunreddy.lightstreamassignment.location.api.modal.RickAndMortyLocation

interface RickAndMortyLocationLocalRepository {

    suspend fun getCachedLocations(): List<RickAndMortyLocation>

    suspend fun insertLocations(locations: List<RickAndMortyLocation>)
}