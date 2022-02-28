package com.wizeline.mobilenews.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.domain.usecases.SearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class ArticleViewModel @Inject constructor(private val useCase: SearchNewsUseCase) : ViewModel() {

    private val _newsList = MutableLiveData<NetworkResults<List<Article>>>()
    val newsList: LiveData<NetworkResults<List<Article>>>
        get() = _newsList

    private val _queryForSearch = MutableLiveData<String>()

    var queryForSearch: String
        get() = _queryForSearch.value ?: ""
        set(text) {
            _queryForSearch.value = text
        }

    private fun getArticles() {
        viewModelScope.launch(Dispatchers.IO) {
            _newsList.postValue(
                useCase.invoke(
                    query = "android",
                    dateFrom = "",
                    dateTo = "",
                    sortBy = "",
                    pageSize = 10,
                    page = 1
                )
            )
        }
    }

    init {
        getArticles()
    }

}