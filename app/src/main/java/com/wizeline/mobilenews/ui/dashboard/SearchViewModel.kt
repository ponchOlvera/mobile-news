package com.wizeline.mobilenews.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.LazyPagingItems
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.domain.usecases.SearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor(private val useCase: SearchNewsUseCase) : ViewModel() {

    lateinit var lazyArticles: LazyPagingItems<Article>
    var articleClickedPos: Int = 0

    fun getArticlesBySearch(queryForSearch: String): LiveData<PagingData<Article>> {
        return useCase.getFilteredArticles(queryForSearch).cachedIn(viewModelScope)
    }

}