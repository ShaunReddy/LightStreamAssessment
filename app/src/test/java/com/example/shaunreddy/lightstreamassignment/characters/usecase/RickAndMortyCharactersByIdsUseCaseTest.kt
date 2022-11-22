package com.example.shaunreddy.lightstreamassignment.characters.usecase

import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Character
import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Location
import com.example.shaunreddy.lightstreamassignment.characters.repository.RickAndMortyCharacterRepositoryImpl
import com.example.shaunreddy.lightstreamassignment.domain.util.Success
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectIndexed
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import retrofit2.Response


class RickAndMortyCharactersByIdsUseCaseTest {

    private lateinit var useCase: RickAndMortyCharactersByIdsUseCase
    private val mockRepositoryImpl = mockk<RickAndMortyCharacterRepositoryImpl>(relaxed = true)
    private val character = Character(
        "",
        "",
        1,
        "",
        mockk<Location>(),
        "Rick",
        mockk<Location>(),
        "",
        "",
        ""
    )
    private val mockResponse = listOf(
        character,
        character.copy(name = "Morty")
    )

    private val mockIds = listOf(1, 2, 3, 4)


    @Before
    fun setUp() {
        useCase = RickAndMortyCharactersByIdsUseCase(
            mockRepositoryImpl
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test success`() = runBlockingTest {
        coEvery { mockRepositoryImpl.getCharactersByIds(mockIds) } returns Response.success(
            mockResponse
        )
        useCase.execute(mockIds).collectIndexed { index, value ->
            if(index==0){
                assertThat(value.isLoading).isTrue()
            } else {
                coVerify(exactly = 1) { mockRepositoryImpl.getCharactersByIds(mockIds) }
                assertThat(value.isLoading).isFalse()
                assertThat(value.isFailure).isFalse()
                assertThat(value.isSuccess).isTrue()
                assertThat((value as Success).response.size).isEqualTo(2)
                assertThat(value.response[0].name).isEqualTo("Morty")
                assertThat(value.response[1].name).isEqualTo("Rick")
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `test failure`() = runBlockingTest {
        coEvery { mockRepositoryImpl.getCharactersByIds(mockIds) } returns Response.error(
            400,
            mockk(relaxed = true)
        )
        useCase.execute(mockIds).take(1).collectIndexed { index, value ->
            if(index==0){
                assertThat(value.isLoading).isTrue()
            } else {
                coVerify(exactly = 1) { mockRepositoryImpl.getCharactersByIds(mockIds) }
                assertThat(value.isLoading).isFalse()
                assertThat(value.isFailure).isTrue()
                assertThat(value.isSuccess).isFalse()
            }
        }
    }
}