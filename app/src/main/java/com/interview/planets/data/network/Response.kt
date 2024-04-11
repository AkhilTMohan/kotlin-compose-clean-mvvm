package com.interview.planets.data.network

import com.interview.planets.data.models.Planet

sealed class Response<out T> {
    enum class ErrorTypes {
        NO_NETWORK,
        ERROR
    }

    data class Success<out T>(val data: Planet?) : Response<T>()
    data class Error<out T>(val exception: Exception) : Response<T>() {
        /** This is may not be accurate, this can be achieved  with connectivityManager, adding this
         * as a improvement */
        val type: ErrorTypes =
            if (exception.localizedMessage?.contains("Unable to resolve host", true) == true) {
                ErrorTypes.NO_NETWORK
            } else {
                ErrorTypes.ERROR
            }
    }
}