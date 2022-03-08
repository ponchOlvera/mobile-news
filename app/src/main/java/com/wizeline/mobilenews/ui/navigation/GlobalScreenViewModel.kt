package com.wizeline.mobilenews.ui.navigation

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.domain.usecases.SearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class GlobalScreenViewModel @Inject constructor(private val useCase: SearchNewsUseCase) : ViewModel()  {

    fun getArticles(): LiveData<PagingData<Article>> {
        return useCase.getAllArticles().cachedIn(viewModelScope)
    }
}