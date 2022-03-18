package com.wizeline.mobilenews.domain.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.wizeline.mobilenews.PAGE_SIZE
import com.wizeline.mobilenews.data.apiservice.NewscatcherApiService
import com.wizeline.mobilenews.domain.extensions.orOne
import com.wizeline.mobilenews.domain.models.NewsArticle

class NewsSearchPagingSource(
    private val retrofitService: NewscatcherApiService,
    private val search: String,
) : PagingSource<Int, NewsArticle>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsArticle> {
        val position = params.key.orOne()
        return if (search.isEmpty()) {
            LoadResult.Error(Exception("EMPTY LIST"))
        } else {
            val response =
                retrofitService.searchNews(query = search, pageSize = PAGE_SIZE, page = position)
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

    override fun getRefreshKey(state: PagingState<Int, NewsArticle>): Int? {
        return state.anchorPosition
    }
}