package com.wizeline.mobilenews.data.models

sealed class NetworkResults<T>(
    val data: T? = null,
    val errorMessage: String? = null
) {
    class Success<T>(data: T?) : NetworkResults<T>(data)
    class Error<T>(errorMessage: String, data: T? = null) : NetworkResults<T>(data, errorMessage)
    class Loading<T>() : NetworkResults<T>()
}
