package com.wizeline.mobilenews.data.repo

import com.wizeline.mobilenews.data.apiservice.NewscatcherApiService
import com.wizeline.mobilenews.data.mappers.SafeApiCall
import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.data.models.NewsRaw
import javax.inject.Inject

class RetrofitRepository @Inject constructor(private val retrofitService: NewscatcherApiService) :
    SafeApiCall() /* TODO: Add interface implementation*/ {

    suspend fun searchNews(
        query: String,
        dateFrom: String?,
        dateTo: String?,
        sortBy: String?,
        pageSize: Int?,
        page: Int?
    ): NetworkResults<NewsRaw> {
        return doSafeCall {
            retrofitService.searchNews(query, dateFrom, dateTo, sortBy, pageSize, page)
        }
    }
}
