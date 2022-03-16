

package com.wizeline.mobilenews.ui.dashboard

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import androidx.paging.compose.collectAsLazyPagingItems
import com.wizeline.mobilenews.ui.custom.SliderArticles
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun GlobalNewsPage() {
    val viewModel: GlobalNewsViewModel = hiltViewModel()
    val articles = viewModel.getArticles().asFlow()
    val lazyArticles = articles.collectAsLazyPagingItems()
    SliderArticles(lazyArticles)
}