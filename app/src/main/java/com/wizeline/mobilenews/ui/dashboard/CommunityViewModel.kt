package com.wizeline.mobilenews.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.domain.usecases.GetCommunityNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CommunityViewModel @Inject constructor(private val useCase: GetCommunityNewsUseCase) : ViewModel()  {

    fun getArticles(): LiveData<PagingData<Article>> {
        return useCase.getAllArticles().cachedIn(viewModelScope)
    }
}

