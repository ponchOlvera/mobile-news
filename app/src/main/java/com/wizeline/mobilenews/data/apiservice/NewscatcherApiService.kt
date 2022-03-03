package com.wizeline.mobilenews.data.apiservice

import com.wizeline.mobilenews.data.apiservice.NewscatcherApi.SEARCH_NEWS
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
        @Query("q")
        query: String,
       /* @Query("from")
        dateFrom: String?,
        @Query("to")
        dateTo: String?,
        @Query("sort_by")
        sortBy: String?,*/
        @Query("page_size")
        pageSize: Int?,
        @Query("page")
        page: Int?
    ): Response<NewsRaw>
}
