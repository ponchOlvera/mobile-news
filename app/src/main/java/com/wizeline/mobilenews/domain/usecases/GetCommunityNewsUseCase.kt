package com.wizeline.mobilenews.domain.usecases

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.domain.models.CommunityArticle

interface GetCommunityNewsUseCase {

    suspend operator fun invoke(
        query: List<String> = emptyList(),
        dateFrom: Long = System.currentTimeMillis(),
        dateTo: Long? = null,
        pageSize: Int = 20,
        page: Int = 1
    ): List<CommunityArticle>

    fun getAllArticles(): LiveData<PagingData<Article>>
}