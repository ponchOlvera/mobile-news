package com.wizeline.mobilenews.data.di

import androidx.paging.PagingConfig
import com.wizeline.mobilenews.PAGE_SIZE_COMMUNITY
import com.wizeline.mobilenews.data.apiservice.NewscatcherApiService
import com.wizeline.mobilenews.data.apiservice.RetrofitProvider
import com.wizeline.mobilenews.data.apiservice.SupportInterceptor
import com.wizeline.mobilenews.data.db.firebase.FirebaseFirestoreManager
import com.wizeline.mobilenews.data.db.firebase.FirebaseFirestoreManager.Companion.ARTICLE_COLLECTION
import com.wizeline.mobilenews.data.repo.FirebaseRepository
import com.wizeline.mobilenews.domain.data_source.FirestorePagingSource
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
    fun provideFirestorePagingSource(
        firebaseFirestoreManager: FirebaseFirestoreManager
    ) = FirestorePagingSource(firebaseFirestoreManager)

    @Provides
    fun providePagingConfig() = PagingConfig(
        pageSize = PAGE_SIZE_COMMUNITY
    )

    @Provides
    @Singleton
    fun provideFirebaseRepository(
        firebaseFirestoreManager: FirebaseFirestoreManager,
        datasource: FirestorePagingSource,
        config: PagingConfig
    ): FirebaseRepository =
        FirebaseRepository(firebaseFirestoreManager, datasource, config)

    @Provides
    @Singleton
    fun provideFirebaseFirestoreManager(): FirebaseFirestoreManager =
        FirebaseFirestoreManager(ARTICLE_COLLECTION)

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
