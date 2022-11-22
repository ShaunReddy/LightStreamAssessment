package com.example.shaunreddy.lightstreamassignment.location.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shaunreddy.lightstreamassignment.domain.util.AsyncOperation
import com.example.shaunreddy.lightstreamassignment.location.api.modal.RickAndMortyLocation
import com.example.shaunreddy.lightstreamassignment.location.usecase.RickAndMortyLocationUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.parameter.parametersOf
import org.koin.java.KoinJavaComponent.inject

class RickAndMortyLocationsViewModel: ViewModel() {

    private val usecase: RickAndMortyLocationUseCase by inject(
        RickAndMortyLocationUseCase::class.java
    ) {
        parametersOf(viewModelScope)
    }

    private val ioDispatcher = Dispatchers.IO

    private val _locationsLiveData = MutableLiveData<AsyncOperation<List<RickAndMortyLocation>>>()

    val locationsLiveData = _locationsLiveData

    init {
        getLocations()
    }

    fun getLocations() {
        viewModelScope.launch(ioDispatcher) {
            usecase.execute().collect{
                _locationsLiveData.postValue(it)
            }
        }
    }
}