package com.wizeline.mobilenews.domain.repositories

import android.net.Uri
import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.domain.models.CommunityArticle

interface CommunityRepository {

    suspend fun searchNews(
        query: List<String>,
        dateFrom: Long,
        dateTo: Long?,
        pageSize: Int,
        page: Int,
    ): List<CommunityArticle>

    suspend fun saveArticle(
        communityArticle: CommunityArticle,
        imageUri: Uri
    ): NetworkResults<String?>

}
