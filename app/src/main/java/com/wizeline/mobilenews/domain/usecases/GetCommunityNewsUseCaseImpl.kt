package com.wizeline.mobilenews.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagingData
import androidx.paging.map
import com.wizeline.mobilenews.data.mappers.NewsDataToNewsDomain
import com.wizeline.mobilenews.domain.extensions.toArticle
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.domain.models.CommunityArticle
import com.wizeline.mobilenews.domain.repositories.CommunityRepository
import javax.inject.Inject

class GetCommunityNewsUseCaseImpl @Inject constructor(
    private val communityRepository: CommunityRepository
) : GetCommunityNewsUseCase, NewsDataToNewsDomain<CommunityArticle, Article>() {
//    override suspend fun invoke(
//        query: List<String>,
//        dateFrom: Long,
//        dateTo: Long?,
//        pageSize: Int,
//        page: Int
//    ): List<CommunityArticle> =
//        communityRepository.searchNews(query, dateFrom, dateTo, pageSize, page)

//    override fun invoke(): LiveData<PagingData<CommunityArticle>> {
//        return communityRepository.getArticles()
//    }

    override fun getAllArticles(): LiveData<PagingData<Article>> {
        return Transformations.map(communityRepository.getArticles()) { list ->
            list.map { it.toArticle() }
        }
    }
}