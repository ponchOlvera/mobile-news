package com.wizeline.mobilenews.ui.dashboard

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.wizeline.mobilenews.DEFAULT_ARTICLE_IMG
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.ui.custom.SliderArticles
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun SearchResults(viewModel: ArticleViewModel) {
    val articles = viewModel.lazyArticles
    println("parcelable: ${articles.itemSnapshotList}")
    SliderArticles(articles, viewModel.articleClickedPos)

}