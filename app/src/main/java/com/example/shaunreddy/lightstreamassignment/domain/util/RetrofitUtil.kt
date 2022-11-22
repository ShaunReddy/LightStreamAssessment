package com.example.shaunreddy.lightstreamassignment.domain.util

import retrofit2.Response

inline fun <In, Out> handleRetrofitResponse(
    request:() -> Response<In>,
    onSuccess: () -> AsyncOperation<Out>,
    onSaveToLocalDB: (In?) -> Unit,
    onFailure: () -> Failure<Out>
): AsyncOperation<Out> {
    return try {
        val response = request()
        when {
            response.isSuccessful -> {
                onSaveToLocalDB(response.body())
                onSuccess()
            }
            else -> onFailure()
        }
    } catch (e: java.lang.Exception) {
        onFailure()
    }
}