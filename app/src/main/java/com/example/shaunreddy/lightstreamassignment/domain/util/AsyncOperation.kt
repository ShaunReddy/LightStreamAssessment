package com.example.shaunreddy.lightstreamassignment.domain.util

sealed class AsyncOperation<out T> {
    val isLoading by lazy { this is Loading }
    val isSuccess by lazy { this is Success}
    val isFailure by lazy { this is Failure}
}

class Loading<T>(val cachedResponse: T): AsyncOperation<T>()

class Success<T>(val response: T): AsyncOperation<T>()

class Failure<T>(val cachedResponse: T): AsyncOperation<T>()