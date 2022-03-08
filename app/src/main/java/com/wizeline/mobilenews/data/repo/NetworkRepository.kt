package com.wizeline.mobilenews.data.repo

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.wizeline.mobilenews.data.apiservice.NewscatcherApiService
import com.wizeline.mobilenews.domain.data_source.NewsSearchPagingSource
import com.wizeline.mobilenews.domain.models.NewsArticle
import com.wizeline.mobilenews.domain.repositories.NewsRepository
import javax.inject.Inject

class NetworkRepository @Inject constructor(
    private val retrofitService: NewscatcherApiService,
    private val dataSource: PagingSource<Int, NewsArticle>
) : NewsRepository {

    override fun searchNews(
        query: String,
    ): LiveData<PagingData<NewsArticle>> {
        return Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = true,
                maxSize = 60,
                prefetchDistance = 5,
                initialLoadSize = 50
            ),
            pagingSourceFactory = {
                NewsSearchPagingSource(
                    retrofitService,
                    query
                )
            }).liveData
    }

    override fun allNews(): LiveData<PagingData<NewsArticle>> {
        return Pager(
            config = PagingConfig(
                pageSize = 50,
                enablePlaceholders = true,
                maxSize = 60,
                prefetchDistance = 5,
                initialLoadSize = 50
            ),
            pagingSourceFactory = {
                dataSource
            }).liveData
    }
}
