package com.wizeline.mobilenews.domain.usecases

import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.domain.models.NewsArticle


interface SearchNewsUseCase {
    suspend operator fun invoke(
        query: String,
        dateFrom: String?,
        dateTo: String?,
        sortBy: String?,
        pageSize: Int?,
        page: Int?
    ): NetworkResults<List<Article>>
}