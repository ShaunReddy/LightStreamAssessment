package com.example.shaunreddy.lightstreamassignment.location.usecase

import com.example.shaunreddy.lightstreamassignment.location.api.modal.Location
import com.example.shaunreddy.lightstreamassignment.location.api.modal.RickAndMortyLocationsResponse
import com.example.shaunreddy.lightstreamassignment.location.repository.RickAndMortyLocationLocalRepositoryImpl
import com.example.shaunreddy.lightstreamassignment.location.repository.RickAndMortyLocationRepositoryImpl
import com.google.common.truth.Truth
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class RickAndMortyLocationUseCaseTest {
    private lateinit var useCase: RickAndMortyLocationUseCase
    private val mockRepositoryImpl = mockk<RickAndMortyLocationRepositoryImpl>(relaxed = true)
    private val mockLocalRepositoryImpl = mockk<RickAndMortyLocationLocalRepositoryImpl>(relaxed = true)
    private val location = Location(
        "",
        "",
        1,
        "Earth",
        listOf(),
        "Plane",
        ""
    )

    private val mockResponse = RickAndMortyLocationsResponse(
        listOf(location)
    )

    @Before
    fun setUp() {
        useCase = RickAndMortyLocationUseCase(
            mockRepositoryImpl,
            mockLocalRepositoryImpl,
            TestCoroutineScope()
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test success`() = runBlockingTest {
        coEvery { mockRepositoryImpl.getLocations() } returns Response.success(mockResponse)
        useCase.execute().take(1).collect {
            coVerify (exactly = 1){ mockRepositoryImpl.getLocations() }
            coVerify { mockLocalRepositoryImpl.insertLocations(any()) }
            Truth.assertThat(it.isLoading).isFalse()
            Truth.assertThat(it.isSuccess).isTrue()
            Truth.assertThat(it.isFailure).isFalse()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test failure`() = runBlockingTest {
        coEvery { mockRepositoryImpl.getLocations() } returns Response.error(400, mockk(relaxed = true))
        useCase.execute().take(1).collect {
            coVerify (exactly = 1){ mockRepositoryImpl.getLocations() }
            coVerify { mockLocalRepositoryImpl.getCachedLocations() }
            Truth.assertThat(it.isLoading).isFalse()
            Truth.assertThat(it.isFailure).isTrue()
            Truth.assertThat(it.isSuccess).isFalse()
        }
    }



}