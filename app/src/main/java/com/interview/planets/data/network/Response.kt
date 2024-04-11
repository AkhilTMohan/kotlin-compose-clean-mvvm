package com.interview.planets.data.network

import com.interview.planets.data.models.Planet

sealed class Response<out T> {
    enum class ERROR_TYPE {
        NO_NETWORK,
        ERROR
    }

    data class Success<out T>(val data: Planet?) : Response<T>()
    data class Error<out T>(val exception: Exception) : Response<T>() {
        val type: ERROR_TYPE =
            if (exception.localizedMessage?.contains("Unable to resolve host", true) == true) {
                ERROR_TYPE.NO_NETWORK
            } else {
                ERROR_TYPE.ERROR
            }
    }
}