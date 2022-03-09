package com.wizeline.mobilenews.domain.usecases

import com.wizeline.mobilenews.data.mappers.NewsDataToNewsDomain
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.domain.models.CommunityArticle
import com.wizeline.mobilenews.domain.repositories.CommunityRepository
import javax.inject.Inject

class GetCommunityNewsUseCaseImpl @Inject constructor(
    private val communityRepository: CommunityRepository
) : GetCommunityNewsUseCase, NewsDataToNewsDomain<CommunityArticle, Article>() {
    override suspend fun invoke(
        query: List<String>,
        dateFrom: Long,
        dateTo: Long?,
        pageSize: Int,
        page: Int
    ): List<CommunityArticle> =
        communityRepository.searchNews(query, dateFrom, dateTo, pageSize, page)
}