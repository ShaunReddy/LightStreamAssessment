package com.example.shaunreddy.lightstreamassignment.characters.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Character
import com.example.shaunreddy.lightstreamassignment.characters.usecase.RickAndMortyCharacterOverViewUseCase
import com.example.shaunreddy.lightstreamassignment.domain.util.AsyncOperation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject

class RickAndMortyCharactersViewModel : ViewModel() {

    private val ioDispatcher = Dispatchers.IO
    private val useCase: RickAndMortyCharacterOverViewUseCase by inject(
        RickAndMortyCharacterOverViewUseCase::class.java
    ) {
        parametersOf(viewModelScope)
    }
    private val _charactersLiveData = MutableLiveData<AsyncOperation<List<Character>>>()
    val charactersLiveData: LiveData<AsyncOperation<List<Character>>> = _charactersLiveData

    init {
        getCharacters()
    }

    fun getCharacters() {
        viewModelScope.launch(ioDispatcher) {
            useCase.execute().collect {
                _charactersLiveData.postValue(it)
            }
        }
    }

}