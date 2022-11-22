package com.example.shaunreddy.lightstreamassignment.location.usecase

import androidx.core.text.isDigitsOnly
import com.example.shaunreddy.lightstreamassignment.location.api.modal.RickAndMortyLocation
import com.example.shaunreddy.lightstreamassignment.location.api.modal.RickAndMortyLocationsResponse

fun RickAndMortyLocationsResponse.toRickAndMortyLocation(): List<RickAndMortyLocation> {
    return results.map { location ->
        RickAndMortyLocation(
            dimension = location.dimension,
            id = location.id,
            name = location.name,
            residents = location.residents.toLocationIds(),
            type = location.type
        )
    }
}

fun List<String>.toLocationIds(): List<Int> {
    return filter {
        it.substringAfterLast("/").isDigitsOnly()
    }.map {
        it.substringAfterLast("/").toInt()
    }
}