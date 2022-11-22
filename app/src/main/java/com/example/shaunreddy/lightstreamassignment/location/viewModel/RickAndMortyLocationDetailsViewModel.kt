package com.example.shaunreddy.lightstreamassignment.location.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shaunreddy.lightstreamassignment.characters.usecase.RickAndMortyCharactersByIdsUseCase
import com.example.shaunreddy.lightstreamassignment.characters.api.modal.Character
import com.example.shaunreddy.lightstreamassignment.domain.util.AsyncOperation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject

class RickAndMortyLocationDetailsViewModel: ViewModel() {

    private val _charactersLiveData = MutableLiveData<AsyncOperation<List<Character>>>()
    val charactersLiveData: LiveData<AsyncOperation<List<Character>>> = _charactersLiveData
    private val useCase: RickAndMortyCharactersByIdsUseCase by inject(RickAndMortyCharactersByIdsUseCase::class.java)

    fun getCharactersByIds(ids: List<Int>){
        viewModelScope.launch(Dispatchers.IO) {
            useCase.execute(ids).collect{
                _charactersLiveData.postValue(it)
            }
        }
    }
}