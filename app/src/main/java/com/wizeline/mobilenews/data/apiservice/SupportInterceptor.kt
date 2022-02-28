package com.wizeline.mobilenews.data.apiservice

import okhttp3.Interceptor
import okhttp3.Response

class SupportInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request = request.newBuilder()
            .addHeader("x-api-key", "7qNHXmc9kGDBk1vbfBJIhGS-b9HnFmbRCu1QQiKBOz8")
            .build()
        return chain.proceed(request)
    }
}
