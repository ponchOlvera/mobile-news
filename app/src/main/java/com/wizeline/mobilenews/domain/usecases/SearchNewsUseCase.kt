package com.wizeline.mobilenews.domain.usecases

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.wizeline.mobilenews.domain.models.Article


interface SearchNewsUseCase {
    fun getFilteredArticles(
        query: String
    ): LiveData<PagingData<Article>>

    fun getAllArticles(): LiveData<PagingData<Article>>
}