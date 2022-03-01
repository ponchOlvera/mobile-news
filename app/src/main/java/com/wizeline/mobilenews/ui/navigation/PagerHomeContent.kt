package com.wizeline.mobilenews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.ui.dashboard.ArticleViewModel

@Composable
fun PagerdHomeContent() {
    val viewModel: ArticleViewModel = hiltViewModel()
    val list = viewModel.newsList.observeAsState()

    list.value?.let { result ->
        when (result) {
            is NetworkResults.Error -> {
            }

            is NetworkResults.Loading -> {
            }
            is NetworkResults.Success -> {
                ArticlesPager(result.data!!)
            }
        }
    }
}