package com.wizeline.mobilenews.domain.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wizeline.mobilenews.PAGE_SIZE
import com.wizeline.mobilenews.data.apiservice.NewscatcherApiService
import com.wizeline.mobilenews.domain.models.NewsArticle
import javax.inject.Inject

class AllNewsPagingSource @Inject constructor(private val retrofitService: NewscatcherApiService) :
    PagingSource<Int, NewsArticle>() {
    override fun getRefreshKey(state: PagingState<Int, NewsArticle>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsArticle> {
        val position = params.key ?: 1
        val response =
            retrofitService.searchNews(pageSize = PAGE_SIZE, page = position)
        return if (response.body() == null) {
            LoadResult.Error(
                Exception(response.errorBody().toString())
            )
        } else {
            val responseResult = response.body()
            LoadResult.Page(
                data = responseResult?.articles.orEmpty(),
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == responseResult?.articles?.size) null else position + 1
            )

        }
    }
}