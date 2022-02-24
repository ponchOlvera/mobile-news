package com.wizeline.mobilenews.domain.usecases

import com.wizeline.mobilenews.data.mappers.NewsDataToNewsDomain
import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.domain.extensions.toArticle
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.domain.models.NewsArticle
import com.wizeline.mobilenews.domain.repositories.NewsRepository
import javax.inject.Inject

class SearchNewsUseCaseImpl @Inject constructor(
    private val newsRepository: NewsRepository
): SearchNewsUseCase, NewsDataToNewsDomain<NewsArticle, Article>() {
    override suspend fun invoke(
        query: String,
        dateFrom: String?,
        dateTo: String?,
        sortBy: String?,
        pageSize: Int?,
        page: Int?
    ): NetworkResults<List<Article>>{
        val result = newsRepository.searchNews(query, dateFrom, dateTo, sortBy, pageSize, page)
        return mapNetworkResult(result.data?.articles, NewsArticle::toArticle)
    }
}