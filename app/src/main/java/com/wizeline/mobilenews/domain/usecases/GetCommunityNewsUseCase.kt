package com.wizeline.mobilenews.domain.usecases

import com.wizeline.mobilenews.domain.models.CommunityArticle

interface GetCommunityNewsUseCase {
    suspend operator fun invoke(
        query: List<String> = emptyList(),
        dateFrom: Long = System.currentTimeMillis(),
        dateTo: Long? = null,
        pageSize: Int = 20,
        page: Int = 1
    ): List<CommunityArticle>
}