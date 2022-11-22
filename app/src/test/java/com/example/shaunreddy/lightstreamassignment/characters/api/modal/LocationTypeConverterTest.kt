package com.example.shaunreddy.lightstreamassignment.characters.api.modal

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


class LocationTypeConverterTest {
    private val location = Location(
        name = "Earth",
        url = "https://rickandmortyapi.com/location/1"
    )

    private lateinit var locationTypeConverter: LocationTypeConverter

    @Before
    fun setUp() {
        locationTypeConverter = LocationTypeConverter()
    }

    @Test
    fun `test fromLocation`() {
        val location =  locationTypeConverter.fromLocation(location)
        assertThat(location).isEqualTo("{\"name\":\"Earth\",\"url\":\"https://rickandmortyapi.com/location/1\"}")
    }

    @Test
    fun `test toLocation`() {
        val location =  locationTypeConverter.toLocation(locationTypeConverter.fromLocation(location))
        assertThat(location.name).isEqualTo("Earth")
        assertThat(location.url).isEqualTo("https://rickandmortyapi.com/location/1")
    }

}