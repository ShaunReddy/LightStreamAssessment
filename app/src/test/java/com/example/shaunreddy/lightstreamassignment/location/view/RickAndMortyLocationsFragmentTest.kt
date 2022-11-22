package com.example.shaunreddy.lightstreamassignment.location.view

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
import com.example.shaunreddy.lightstreamassignment.R
import com.example.shaunreddy.lightstreamassignment.domain.util.AsyncOperation
import com.example.shaunreddy.lightstreamassignment.domain.util.Failure
import com.example.shaunreddy.lightstreamassignment.domain.util.Loading
import com.example.shaunreddy.lightstreamassignment.domain.util.Success
import com.example.shaunreddy.lightstreamassignment.location.api.modal.RickAndMortyLocation
import com.example.shaunreddy.lightstreamassignment.location.view.adapter.RickAndMortyLocationsAdapter
import com.example.shaunreddy.lightstreamassignment.location.viewModel.RickAndMortyLocationsViewModel
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.SnackbarContentLayout
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.O_MR1],
)
class RickAndMortyLocationsFragmentTest {

    private val mockLivedata = MutableLiveData<AsyncOperation<List<RickAndMortyLocation>>>()
    private val mockViewModel = mockk<RickAndMortyLocationsViewModel>(relaxed = true) {
        every { locationsLiveData } returns mockLivedata
    }

    private val mockLocation = RickAndMortyLocation(
        "",
        1,
        "Earth",
        listOf(),
        "Planet"
    )

    private lateinit var scenario: FragmentScenario<RickAndMortyLocationsFragment>
    private val mockNavController = mockk<NavController>(relaxed = true)


    @Before
    fun setUp() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_LightStreamAssignment) {
            RickAndMortyLocationsFragment(object : ViewModelProvider.Factory {
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
            Assertions.assertNotNull(it.binding.rickandmortyRv.adapter)
        }
        stopKoin()
    }

    @Test
    fun `test Failed fetching items`() {
        mockLivedata.postValue(Failure(listOf(mockLocation)))
        scenario.onFragment {
            val snackbarContentLayout = (it.snackbar.view as Snackbar.SnackbarLayout).getChildAt(0) as SnackbarContentLayout
            assertThat(snackbarContentLayout.messageView).isNotNull()
            assertThat(snackbarContentLayout.messageView.text).isEqualTo("Failed to fetch latest data.")
            with(it.binding.rickandmortyRv) {
                Assertions.assertEquals(adapter?.itemCount, 1)
                val viewHolder = findViewHolderForAdapterPosition(0) as RickAndMortyLocationsAdapter.RickAndMortyLocationsViewHolder
                assertThat(
                    viewHolder.itemView.findViewById<TextView>(R.id.rickandmorty_locations_name).text
                ).isEqualTo("Earth")

                assertThat(
                    viewHolder.itemView.findViewById<TextView>(R.id.rickandmorty_locations_type).text
                ).isEqualTo("Planet")

                viewHolder.itemView.performClick()
                verify { mockNavController.navigate(any<NavDirections>()) }
            }
        }
        stopKoin()
    }

    @Test
    fun `test successful items fetch`() {
        mockLivedata.postValue(Success(listOf(mockLocation)))
        scenario.onFragment {
            Assertions.assertEquals(it.binding.rickandmortyRv.adapter?.itemCount, 1)
            with(it.binding.rickandmortyRv) {
                Assertions.assertEquals(adapter?.itemCount, 1)
                val viewHolder = findViewHolderForAdapterPosition(0) as RickAndMortyLocationsAdapter.RickAndMortyLocationsViewHolder
                assertThat(
                    viewHolder.itemView.findViewById<TextView>(R.id.rickandmorty_locations_name).text
                ).isEqualTo("Earth")

                assertThat(
                    viewHolder.itemView.findViewById<TextView>(R.id.rickandmorty_locations_type).text
                ).isEqualTo("Planet")

                viewHolder.itemView.performClick()
                verify { mockNavController.navigate(any<NavDirections>()) }

            }
        }
        stopKoin()
    }

    @Test
    fun `test loading items fetch`() {
        mockLivedata.postValue(Loading(listOf(mockLocation)))
        scenario.onFragment {

            val snackbarContentLayout = (it.snackbar.view as Snackbar.SnackbarLayout).getChildAt(0) as SnackbarContentLayout
            assertThat(snackbarContentLayout.messageView).isNotNull()
            assertThat(snackbarContentLayout.messageView.text).isEqualTo("Fetching latest data...")

            Assertions.assertEquals(it.binding.rickandmortyRv.adapter?.itemCount, 1)
            with(it.binding.rickandmortyRv) {
                Assertions.assertEquals(adapter?.itemCount, 1)
                val viewHolder = findViewHolderForAdapterPosition(0) as RickAndMortyLocationsAdapter.RickAndMortyLocationsViewHolder
                assertThat(
                    viewHolder.itemView.findViewById<TextView>(R.id.rickandmorty_locations_name).text
                ).isEqualTo("Earth")

                assertThat(
                    viewHolder.itemView.findViewById<TextView>(R.id.rickandmorty_locations_type).text
                ).isEqualTo("Planet")

                viewHolder.itemView.performClick()
                verify { mockNavController.navigate(any<NavDirections>()) }
            }
        }
        stopKoin()
    }

}