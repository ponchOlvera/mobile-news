package com.wizeline.mobilenews.domain.usecases

import com.wizeline.mobilenews.data.mappers.NewsDataToNewsDomain
import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.domain.extensions.toArticle
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.domain.models.CommunityArticle
import com.wizeline.mobilenews.domain.models.NewsArticle
import com.wizeline.mobilenews.domain.repositories.CommunityRepository
import com.wizeline.mobilenews.domain.repositories.NewsRepository

class GetCommunityNewsUseCaseImpl(
    private val communityRepository: CommunityRepository
): GetCommunityNewsUseCase, NewsDataToNewsDomain<CommunityArticle, Article>() {
    override suspend fun invoke(
        dateFrom: String?,
        dateTo: String?,
        pageSize: Int?,
        page: Int?
    ): NetworkResults<List<Article>> {
        val result = communityRepository.getNews(dateFrom, dateTo, pageSize, page)
        return mapNetworkResult(result.data?.articles, CommunityArticle::toArticle)
    }
}