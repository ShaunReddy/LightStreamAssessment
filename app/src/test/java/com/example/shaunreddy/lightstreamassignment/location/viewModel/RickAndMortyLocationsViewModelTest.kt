package com.example.shaunreddy.lightstreamassignment.location.viewModel

import com.example.shaunreddy.lightstreamassignment.location.usecase.RickAndMortyLocationUseCase
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest


class RickAndMortyLocationsViewModelTest : KoinTest {

    private lateinit var mockModule: Module
    private lateinit var useCase: RickAndMortyLocationUseCase
    private lateinit var viewModel: RickAndMortyLocationsViewModel

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
    fun `test viewModel init calls getCharacters()`() {
        viewModel = RickAndMortyLocationsViewModel()
        coVerify { useCase.execute() }
    }


}