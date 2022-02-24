package com.wizeline.mobilenews.ui.dashboard

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp

@Composable
fun DashboardHomeContent(navigateToDetail: (DummyArticle) -> Unit) {
    val articles = remember { listOf(DummyArticle(), DummyArticle(), DummyArticle()) }
    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
        items(
            items = articles,
            itemContent = {
                ArticleListItem(article = it, navigateToDetail)
            }
        )
    }

}