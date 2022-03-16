package com.wizeline.mobilenews.data.di

import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.wizeline.mobilenews.PAGE_SIZE
import com.wizeline.mobilenews.data.apiservice.NewscatcherApiService
import com.wizeline.mobilenews.data.apiservice.RetrofitProvider
import com.wizeline.mobilenews.data.apiservice.SupportInterceptor
import com.wizeline.mobilenews.data.db.firebase.FirebaseFirestoreManager
import com.wizeline.mobilenews.data.db.firebase.FirebaseFirestoreManager.Companion.ARTICLE_COLLECTION
import com.wizeline.mobilenews.data.repo.FirebaseRepository
import com.wizeline.mobilenews.data.repo.NetworkRepository
import com.wizeline.mobilenews.domain.data_source.FirestorePagingSource
import com.wizeline.mobilenews.domain.models.CommunityArticle
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

  /*  @Provides
    @Singleton
    fun provideNetworkRepository(
        newscatcherApiService: NewscatcherApiService
    ): NetworkRepository = NetworkRepository(newscatcherApiService)*/

    @Provides
    fun provideQueryProductsByName() = FirebaseFirestore.getInstance()
        .collection(ARTICLE_COLLECTION)
//        .orderBy("author", ASCENDING)
        .limit(PAGE_SIZE.toLong())

    @Provides
    fun provideFirestorePagingSource(
        queryProductsByName: Query
    ) = FirestorePagingSource(queryProductsByName)

    @Provides
    fun providePagingConfig() = PagingConfig(
        pageSize = PAGE_SIZE
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
