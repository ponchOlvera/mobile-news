package com.wizeline.mobilenews.ui.navigation

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.wizeline.mobilenews.domain.models.Article


//https://github.com/google/accompanist/blob/main/sample/src/main/java/com/google/accompanist/sample/pager/HorizontalPagerTransitionSample.kt

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ArticlesPager(articles: List<Article>) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HorizontalPager(
            count = articles.size,
            modifier = Modifier
                .fillMaxHeight()
        ) { page ->
            ScrollableArticle(articles[page])
        }
    }
}
