package com.wizeline.mobilenews.ui.navigation

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.paging.compose.LazyPagingItems
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.wizeline.mobilenews.domain.models.Article

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ArticlesPager(articles: LazyPagingItems<Article>) {
    val pagerState = rememberPagerState(pageCount = 50)
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxHeight()
        ) { page ->
            articles[page]?.let { article ->
                ScrollableArticle(article)
            }

        }
    }
}
