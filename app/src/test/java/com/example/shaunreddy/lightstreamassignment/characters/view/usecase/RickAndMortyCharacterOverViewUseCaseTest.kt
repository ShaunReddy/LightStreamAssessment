package com.example.shaunreddy.lightstreamassignment.characters.view.usecase

import com.example.shaunreddy.lightstreamassignment.characters.api.modal.CharactersResponse
import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Location
import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Character
import com.example.shaunreddy.lightstreamassignment.characters.usecase.RickAndMortyCharacterOverViewUseCase
import com.example.shaunreddy.lightstreamassignment.characters.repository.RickAndMortyCharacterRepositoryImpl
import com.example.shaunreddy.lightstreamassignment.characters.repository.RickAndMortyCharactersLocalRepositoryImpl
import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import io.mockk.coVerify
import io.mockk.clearAllMocks
import io.mockk.coEvery
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class RickAndMortyCharacterOverViewUseCaseTest {


    private lateinit var useCase: RickAndMortyCharacterOverViewUseCase
    private val mockRepositoryImpl = mockk<RickAndMortyCharacterRepositoryImpl>(relaxed = true)
    private val mockLocalRepositoryImpl = mockk<RickAndMortyCharactersLocalRepositoryImpl>(relaxed = true)
    private val mockResponse = CharactersResponse(
        listOf(
            Character(
                "",
                "",
                1,
                "",
                mockk<Location>(),
                "",
                mockk<Location>(),
                "",
                "",
                ""
            )
        )
    )


    @Before
    fun setUp() {
        useCase = RickAndMortyCharacterOverViewUseCase(
            mockRepositoryImpl,
            mockLocalRepositoryImpl,
            TestCoroutineScope()
        )
    }

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test success`() = runBlockingTest {
        coEvery { mockRepositoryImpl.getCharacters() } returns Response.success(mockResponse)
        useCase.execute().take(1).collect {
            coVerify (exactly = 1){ mockRepositoryImpl.getCharacters() }
            coVerify { mockLocalRepositoryImpl.insertCharacters(any()) }
            assertThat(it.isLoading).isFalse()
            assertThat(it.isSuccess).isTrue()
            assertThat(it.isFailure).isFalse()
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test failure`() = runBlockingTest {
        coEvery { mockRepositoryImpl.getCharacters() } returns Response.error(400, mockk(relaxed = true))
        useCase.execute().take(1).collect {
            coVerify (exactly = 1){ mockRepositoryImpl.getCharacters() }
            coVerify { mockLocalRepositoryImpl.getCachedCharacters() }
            assertThat(it.isLoading).isFalse()
            assertThat(it.isFailure).isTrue()
            assertThat(it.isSuccess).isFalse()
        }
    }

}