package com.wizeline.mobilenews.data.apiservice

import com.wizeline.mobilenews.data.apiservice.NewscatcherApi.LANGUAGE
import com.wizeline.mobilenews.data.apiservice.NewscatcherApi.LATEST_HEADLINES
import com.wizeline.mobilenews.data.apiservice.NewscatcherApi.SEARCH_NEWS
import com.wizeline.mobilenews.data.apiservice.NewscatcherApi.TOPIC
import com.wizeline.mobilenews.data.models.NewsRaw
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewscatcherApiService {

    /**
     * Optional parameters are marked as nullable.
     **/
    @GET(SEARCH_NEWS)
    suspend fun searchNews(
        @Query("lang")
        lang: String = LANGUAGE,
        @Query("topic")
        topic: String = TOPIC,
        @Query("q")
        query: String,
        @Query("page_size")
        pageSize: Int?,
        @Query("page")
        page: Int?
    ): Response<NewsRaw>

    @GET(LATEST_HEADLINES)
    suspend fun getAllNews(
        @Query("lang")
        lang: String = LANGUAGE,
        @Query("topic")
        topic: String = TOPIC,
        @Query("page_size")
        pageSize: Int?,
        @Query("page")
        page: Int?
    ): Response<NewsRaw>
}
