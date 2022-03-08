package com.wizeline.mobilenews.domain.repositories

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.wizeline.mobilenews.domain.models.NewsArticle

interface NewsRepository {

    fun searchNews(
        query: String
    ): LiveData<PagingData<NewsArticle>>

    fun allNews(): LiveData<PagingData<NewsArticle>>

}