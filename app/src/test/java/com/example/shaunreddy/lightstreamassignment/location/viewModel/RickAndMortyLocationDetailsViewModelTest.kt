package com.example.shaunreddy.lightstreamassignment.location.viewModel

import com.example.shaunreddy.lightstreamassignment.characters.usecase.RickAndMortyCharactersByIdsUseCase
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module


class RickAndMortyLocationDetailsViewModelTest {
    private lateinit var mockModule: Module
    private lateinit var useCase: RickAndMortyCharactersByIdsUseCase
    private val viewModel: RickAndMortyLocationDetailsViewModel = RickAndMortyLocationDetailsViewModel()

    @Before
    fun setup() {
        useCase = mockk(relaxed = true)
        mockModule = module {
            single {useCase}
        }
        startKoin {
            modules(mockModule)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `test viewModel getCharacters()`() {
        viewModel.getCharactersByIds(listOf())
        coVerify { useCase.execute(any()) }
    }
}