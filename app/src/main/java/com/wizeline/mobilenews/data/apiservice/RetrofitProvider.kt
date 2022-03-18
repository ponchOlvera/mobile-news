package com.wizeline.mobilenews.data.apiservice

import com.wizeline.mobilenews.data.apiservice.NewscatcherApi.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

class RetrofitProvider @Inject constructor(val okHttpClient: OkHttpClient) {

    private lateinit var mRetrofitInstance: Retrofit

    init {
        createNewRetrofitInstance()
    }

    fun getInstance(): NewscatcherApiService {
        return mRetrofitInstance.create(NewscatcherApiService::class.java)
    }

    private fun createNewRetrofitInstance() {
        mRetrofitInstance = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object NewscatcherApi {
    const val BASE_URL = "https://api.newscatcherapi.com/v2/"
    const val SEARCH_NEWS = "search"
    const val LATEST_HEADLINES = "latest_headlines"
    const val LANGUAGE = "es, en"
    const val TOPIC = "tech"
}