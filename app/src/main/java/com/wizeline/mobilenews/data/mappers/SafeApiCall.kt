package com.wizeline.mobilenews.data.mappers

import com.wizeline.mobilenews.data.models.NetworkResults
import retrofit2.HttpException
import retrofit2.Response
import java.lang.NullPointerException

abstract class SafeApiCall {
    suspend fun <T> doSafeCall(apiCall: suspend () -> Response<T>): NetworkResults<T> {
        try {
            val callResponse = apiCall()
            if (callResponse.isSuccessful) {
                val responseBody = callResponse.body()
                responseBody?.let {
                    return NetworkResults.Success(responseBody)
                }
            }
            return onError("${callResponse.code()} ${callResponse.message()}")
        } catch (e: HttpException) {
            return onError(e.message())
        } catch (e: NullPointerException) {
            return onError(e.message ?: "NullPointerException")
        }
    }

    private fun <T> onError(message: String): NetworkResults<T> =
        NetworkResults.Error("Api call failed: $message")
}
