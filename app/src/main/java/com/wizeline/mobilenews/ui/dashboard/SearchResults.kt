package com.wizeline.mobilenews.ui.dashboard

import androidx.compose.runtime.Composable
import com.wizeline.mobilenews.ui.custom.SliderArticles
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun SearchResults(viewModel: ArticleViewModel) {
    val articles = viewModel.lazyArticles
    SliderArticles(articles, viewModel.articleClickedPos)
}