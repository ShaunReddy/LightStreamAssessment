package com.example.shaunreddy.lightstreamassignment.characters.view

import android.os.Build
import android.widget.TextView
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import com.example.shaunreddy.lightstreamassignment.domain.util.AsyncOperation
import org.junit.Before
import org.junit.Test
import com.example.shaunreddy.lightstreamassignment.R
import com.example.shaunreddy.lightstreamassignment.characters.view.adapter.RickAndMortyCharacterOverviewAdapter
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Character
import com.example.shaunreddy.lightstreamassignment.characters.viewModel.RickAndMortyCharactersViewModel
import com.example.shaunreddy.lightstreamassignment.domain.util.Failure
import com.example.shaunreddy.lightstreamassignment.domain.util.Loading
import com.example.shaunreddy.lightstreamassignment.domain.util.Success
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import org.koin.core.context.stopKoin

@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.O_MR1],
)
class RickAndMortyCharactersFragmentTest {

    private val mockCharactersLiveData = MutableLiveData<AsyncOperation<List<Character>>>()
    private val mockViewModel= mockk<RickAndMortyCharactersViewModel>( relaxed = true ) {
        every { charactersLiveData } returns mockCharactersLiveData
    }
    private val mockNavController = mockk<NavController>(relaxed = true)

    private val mockCharacters = listOf(
        Character(
            "",
            "",
            1,
            "",
            mockk(),
            "Rick",
            mockk(),
            "",
            "Alive",
            ""
        )
    )

    private lateinit var scenario: FragmentScenario<RickAndMortyCharactersFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_LightStreamAssignment) {
            RickAndMortyCharactersFragment(object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return mockViewModel as T
                }
            }
            ).apply {
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
    fun `test init`() {
        scenario.onFragment{
            assertNotNull(it.binding.rickandmortyRv.adapter)
        }
        stopKoin()
    }

    @Test
    fun `test Failed fetching items`() {
        mockCharactersLiveData.postValue(Failure(mockCharacters))
        scenario.onFragment {
            val snackbarContentLayout = (it.snackbar.view as Snackbar.SnackbarLayout).getChildAt(0) as SnackbarContentLayout
            assertThat(snackbarContentLayout.messageView).isNotNull()
            assertThat(snackbarContentLayout.messageView.text).isEqualTo("Failed to fetch latest data.")
            with(it.binding.rickandmortyRv) {
                assertEquals(adapter?.itemCount, 1)
                val viewHolder = findViewHolderForAdapterPosition(0) as RickAndMortyCharacterOverviewAdapter.RickAndMortyCharacterOverviewHolder
                assertEquals(
                    viewHolder.itemView.findViewById<TextView>(R.id.rickandmorty_character_overview_name).text,
                    "Rick"
                )
                viewHolder.itemView.performClick()
                verify { mockNavController.navigate(any<NavDirections>()) }
            }
        }
        stopKoin()
    }

    @Test
    fun `test successful items fetch`() {
        mockCharactersLiveData.postValue(Success(mockCharacters))
        scenario.onFragment {
            assertEquals(it.binding.rickandmortyRv.adapter?.itemCount, 1)
            with(it.binding.rickandmortyRv) {
                assertEquals(adapter?.itemCount, 1)
                val viewHolder = findViewHolderForAdapterPosition(0) as RickAndMortyCharacterOverviewAdapter.RickAndMortyCharacterOverviewHolder
                assertEquals(
                    viewHolder.itemView.findViewById<TextView>(R.id.rickandmorty_character_overview_name).text,
                    "Rick"
                )
                viewHolder.itemView.performClick()
                verify { mockNavController.navigate(any<NavDirections>()) }
            }
        }
        stopKoin()
    }


    @Test
    fun `test loading items fetch`() {
        mockCharactersLiveData.postValue(Loading(mockCharacters))
        scenario.onFragment {

            val snackbarContentLayout = (it.snackbar.view as Snackbar.SnackbarLayout).getChildAt(0) as SnackbarContentLayout
            assertThat(snackbarContentLayout.messageView).isNotNull()
            assertThat(snackbarContentLayout.messageView.text).isEqualTo("Fetching latest data...")

            assertEquals(it.binding.rickandmortyRv.adapter?.itemCount, 1)
            with(it.binding.rickandmortyRv) {
                assertEquals(adapter?.itemCount, 1)
                val viewHolder = findViewHolderForAdapterPosition(0) as RickAndMortyCharacterOverviewAdapter.RickAndMortyCharacterOverviewHolder
                assertEquals(
                    viewHolder.itemView.findViewById<TextView>(R.id.rickandmorty_character_overview_name).text,
                    "Rick"
                )
                viewHolder.itemView.performClick()
                verify { mockNavController.navigate(any<NavDirections>()) }
            }
        }
        stopKoin()
    }

}
