package com.wizeline.mobilenews.data.apiservice

import com.wizeline.mobilenews.R
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
    const val BASE_URL = context.getString(R.string.api_base_url)
    const val SEARCH_NEWS = context.getString(R.string.api_search_endpoint)
    const val LATEST_HEADLINES = context.getString(R.string.api_latest_headlines_endpoint)
}
