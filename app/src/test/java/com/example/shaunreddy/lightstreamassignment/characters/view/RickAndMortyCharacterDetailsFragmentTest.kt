package com.example.shaunreddy.lightstreamassignment.characters.view

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Character
import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Location
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE,
    sdk = [Build.VERSION_CODES.O_MR1],
)
class RickAndMortyCharacterDetailsFragmentTest {

    private lateinit var scenario: FragmentScenario<RickAndMortyCharacterDetailsFragment>
    private val character = Character(
        "",
        "Male",
        1,
        "",
        Location("Earth", ""),
        "Rick",
        Location("Earth", ""),
        "Human",
        "Alive",
        ""
    )

    private lateinit var args: Bundle

    @Before
    fun setUp() {
        args = Bundle().apply {
            putParcelable("rickAndMortyCharacterDetails", character)
        }
        scenario = launchFragmentInContainer(args){
            RickAndMortyCharacterDetailsFragment()
        }
    }

    @Test
    fun `test data is displayed correctly`() {
        scenario.onFragment {
            assertThat(it.binding.rickandmortyCharacterDetailsName.text).isEqualTo("Name: Rick")
            assertThat(it.binding.rickandmortyCharacterDetailsSpecies.text).isEqualTo("Species: Human")
            assertThat(it.binding.rickandmortyCharacterDetailsGender.text).isEqualTo("Gender: Male")
            assertThat(it.binding.rickandmortyCharacterDetailsOrigin.text).isEqualTo("Origin: Earth")
        }
        stopKoin()
    }

}