package com.example.shaunreddy.lightstreamassignment.location.api.modal

data class RickAndMortyLocationsResponse(
    val results: List<Location>
)

data class Location(
    val created: String,
    val dimension: String,
    val id: Int,
    val name: String,
    val residents: List<String>,
    val type: String,
    val url: String
)