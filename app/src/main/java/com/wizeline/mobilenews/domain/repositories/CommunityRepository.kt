package com.wizeline.mobilenews.domain.repositories

import com.wizeline.mobilenews.data.models.CommunityNewsRaw
import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.domain.models.CommunityArticle

interface CommunityRepository {

    suspend fun getNews(
        dateFrom: String?,
        dateTo: String?,
        pageSize: Int?,
        page: Int?
    ): NetworkResults<CommunityNewsRaw>

    suspend fun saveArticle(communityArticle: CommunityArticle)

}
