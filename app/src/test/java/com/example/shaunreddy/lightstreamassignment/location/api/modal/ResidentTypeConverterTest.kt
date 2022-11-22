package com.example.shaunreddy.lightstreamassignment.location.api.modal

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


class ResidentTypeConverterTest {

    private val residents = listOf(1, 2, 3, 4)
    private lateinit var residentTypeConverter: ResidentTypeConverter

    @Before
    fun setUp() {
        residentTypeConverter = ResidentTypeConverter()
    }

    @Test
    fun `test fromResident`() {
        val residents = residentTypeConverter.fromResidents(residents)
        assertThat(residents).isEqualTo("[1,2,3,4]")
    }

    @Test
    fun `test toResident`() {
        val residents = residentTypeConverter.toResidents(residentTypeConverter.fromResidents(residents))
        assertThat(residents).hasSize(4)
        assertThat(residents).contains(1)
        assertThat(residents).contains(2)
        assertThat(residents).contains(3)
        assertThat(residents).contains(4)
    }
}