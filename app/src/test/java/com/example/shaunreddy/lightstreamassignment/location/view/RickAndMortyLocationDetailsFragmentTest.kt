package com.example.shaunreddy.lightstreamassignment.location.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Character
import com.example.shaunreddy.lightstreamassignment.domain.util.AsyncOperation
import com.example.shaunreddy.lightstreamassignment.location.api.modal.RickAndMortyLocation
import com.example.shaunreddy.lightstreamassignment.location.viewModel.RickAndMortyLocationDetailsViewModel
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.koin.core.context.stopKoin

@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.O_MR1],
)
class RickAndMortyLocationDetailsFragmentTest {

    private val mockLiveData = MutableLiveData<AsyncOperation<List<Character>>>()
    private val mockViewModel = mockk<RickAndMortyLocationDetailsViewModel>( relaxed = true) {
        every { charactersLiveData } returns mockLiveData
    }

    private lateinit var scenario: FragmentScenario<RickAndMortyLocationDetailsFragment>
    private val mockNavController = mockk<NavController>(relaxed = true)
    private val location = RickAndMortyLocation(
        "C-131",
        1,
        "Earth",
        listOf(1,2,3),
        "Planet"
    )


    @Before
    fun setUp() {
        val args = Bundle().apply {
            putParcelable("rickandMortyLocationDetails", location)
        }
        scenario = launchFragmentInContainer(args) {
            RickAndMortyLocationDetailsFragment(object: ViewModelProvider.Factory{
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return mockViewModel as T
                }
            }).apply {
                viewLifecycleOwnerLiveData.observeForever {
                    if (it != null){
                        Navigation.setViewNavController(
                            requireView(),
                            mockNavController
                        )
                    }
                }
            }
        }
    }

    @Test
    fun `test if data is presented correctly`() {
        scenario.onFragment{
            assertThat(it.binding.rickandmortyLocationDetailsName.text).isEqualTo("Name: Earth")
            assertThat(it.binding.rickandmortyLocationDetailsType.text).isEqualTo("Type: Planet")
            assertThat(it.binding.rickandmortyLocationDetailsDimension.text).isEqualTo("Dimension: C-131")
            assertThat(it.binding.rickandmortyLocationDetailsResidents.text).isEqualTo("Residents:3")
            verify { mockViewModel.getCharactersByIds(any()) }
        }
        stopKoin()
    }

    @Test
    fun `test getCharactersByIds is not called when residents size is 0`() {
        val viewModel = mockk<RickAndMortyLocationDetailsViewModel>( relaxed = true )
        val args = Bundle().apply {
            putParcelable("rickandMortyLocationDetails", location.copy(residents = listOf()))
        }
        launchFragmentInContainer(args) {
            RickAndMortyLocationDetailsFragment(object: ViewModelProvider.Factory{
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return viewModel as T
                }
            }).apply {
                viewLifecycleOwnerLiveData.observeForever {
                    if (it != null){
                        Navigation.setViewNavController(
                            requireView(),
                            mockNavController
                        )
                    }
                }
            }
        }.onFragment{
            assertThat(it.binding.rickandmortyLocationDetailsName.text).isEqualTo("Name: Earth")
            assertThat(it.binding.rickandmortyLocationDetailsType.text).isEqualTo("Type: Planet")
            assertThat(it.binding.rickandmortyLocationDetailsDimension.text).isEqualTo("Dimension: C-131")
            assertThat(it.binding.rickandmortyLocationDetailsResidents.text).isEqualTo("Residents:0")
            verify(exactly = 0) { viewModel.getCharactersByIds(any()) }
        }
        stopKoin()
    }

}