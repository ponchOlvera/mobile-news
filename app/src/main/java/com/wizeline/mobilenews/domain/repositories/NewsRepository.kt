package com.wizeline.mobilenews.domain.repositories

import com.wizeline.mobilenews.data.models.CommunityNewsRaw
import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.data.models.NewsRaw

interface NewsRepository {

    suspend fun searchNews(
        query: String,
        dateFrom: String?,
        dateTo: String?,
        sortBy: String?,
        pageSize: Int?,
        page: Int?
    ): NetworkResults<NewsRaw>

}