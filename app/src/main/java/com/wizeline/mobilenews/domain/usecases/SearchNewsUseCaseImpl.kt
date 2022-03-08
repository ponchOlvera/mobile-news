package com.wizeline.mobilenews.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.PagingData
import androidx.paging.map
import com.wizeline.mobilenews.data.mappers.NewsDataToNewsDomain
import com.wizeline.mobilenews.domain.extensions.toArticle
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.domain.models.NewsArticle
import com.wizeline.mobilenews.domain.repositories.NewsRepository
import javax.inject.Inject

class SearchNewsUseCaseImpl @Inject constructor(
    private val newsRepository: NewsRepository
) : SearchNewsUseCase, NewsDataToNewsDomain<NewsArticle, Article>() {
    override fun getFilteredArticles(
        query: String
    ): LiveData<PagingData<Article>> {
        return Transformations.map(newsRepository.searchNews(query)) { list ->
            list.map { it.toArticle() }
        }
    }

    override fun getAllArticles(): LiveData<PagingData<Article>> {
        return Transformations.map(newsRepository.allNews()) { list ->
            list.map { it.toArticle() }
        }
    }
}