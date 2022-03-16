package com.wizeline.mobilenews.ui.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.wizeline.mobilenews.PAGE_SIZE
import com.wizeline.mobilenews.domain.models.CommunityArticle
import com.wizeline.mobilenews.domain.usecases.GetCommunityNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class CommunityViewModel @Inject constructor(private val useCase: GetCommunityNewsUseCase) : ViewModel()  {

    fun getArticles() {
        viewModelScope.launch {
            val results = useCase()
            println("COMMUNITY RES: $results")
        }
    }
}

