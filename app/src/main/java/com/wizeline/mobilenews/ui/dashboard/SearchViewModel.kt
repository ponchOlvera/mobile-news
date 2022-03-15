package com.wizeline.mobilenews.ui.dashboard

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.wizeline.mobilenews.EMPTY_STRING
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.domain.usecases.SearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor(private val useCase: SearchNewsUseCase) : ViewModel() {

    lateinit var lazyArticles: LazyPagingItems<Article>
        private set
    var articleClickedPos: Int = 0
        private set

    var searchStr: String by mutableStateOf(EMPTY_STRING)
        private set

    fun getArticlesBySearch(queryForSearch: String): LiveData<PagingData<Article>> {
        return useCase.getFilteredArticles(queryForSearch).cachedIn(viewModelScope)
    }

    fun updateSearchStr(search: String) {
        this.searchStr = search
    }

    fun updateLazyArticles(articles: LazyPagingItems<Article>) {
        this.lazyArticles = articles
    }

    fun updateClickedArticle(articlePos: Int) {
        this.articleClickedPos = articlePos
    }

}