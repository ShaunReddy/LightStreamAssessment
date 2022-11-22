package com.example.shaunreddy.lightstreamassignment.domain

import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Character
import com.example.shaunreddy.lightstreamassignment.characters.api.modal.CharactersResponse
import com.example.shaunreddy.lightstreamassignment.location.api.modal.RickAndMortyLocationsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ServiceInterface {

    @GET("api/character")
    suspend fun getCharacters(): Response<CharactersResponse>

    @GET("api/character/{ids}")
    suspend fun getCharactersByIds(
        @Path("ids") ids: List<Int>
    ): Response<List<Character>>

    @GET("api/location")
    suspend fun getLocations(): Response<RickAndMortyLocationsResponse>
}