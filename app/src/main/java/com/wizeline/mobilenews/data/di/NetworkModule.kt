package com.wizeline.mobilenews.data.di

import com.wizeline.mobilenews.data.apiservice.NewscatcherApiService
import com.wizeline.mobilenews.data.apiservice.RetrofitProvider
import com.wizeline.mobilenews.data.apiservice.SupportInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): NewscatcherApiService {
        return RetrofitProvider(okHttpClient).getInstance()
    }

    @Provides
    @Singleton
    fun provideUnsafeOkHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(SupportInterceptor())
        return builder.build()
    }
}
