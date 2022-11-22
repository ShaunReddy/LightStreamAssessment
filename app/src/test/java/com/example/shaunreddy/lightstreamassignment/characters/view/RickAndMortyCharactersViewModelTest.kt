package com.example.shaunreddy.lightstreamassignment.characters.view

import com.example.shaunreddy.lightstreamassignment.characters.usecase.RickAndMortyCharacterOverViewUseCase
import com.example.shaunreddy.lightstreamassignment.characters.viewModel.RickAndMortyCharactersViewModel
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

class RickAndMortyCharactersViewModelTest : KoinTest{

    private lateinit var mockModule: Module
    private lateinit var useCase: RickAndMortyCharacterOverViewUseCase
    private lateinit var viewModel: RickAndMortyCharactersViewModel

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
        viewModel = RickAndMortyCharactersViewModel()
        coVerify { useCase.execute() }
    }

}