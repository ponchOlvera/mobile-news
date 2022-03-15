package com.wizeline.mobilenews.ui.custom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.wizeline.mobilenews.FIRST_LIST_ITEM_POS
import com.wizeline.mobilenews.HALF_PAST_ITEM_LEFT
import com.wizeline.mobilenews.HALF_PAST_ITEM_RIGHT
import com.wizeline.mobilenews.domain.models.Article
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
@Composable
fun SliderArticles(
    articles: LazyPagingItems<Article>,
    navigateToPos: Int = FIRST_LIST_ITEM_POS
) {
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(key1 = Unit, block = {
        coroutineScope.launch {
            listState.scrollToItem(navigateToPos)
        }
    })
    LazyRow(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        content = {
            items(articles.itemCount) { index ->
                articles[index]?.let { article -> CustomScrollableArticle(article) }
                if (!listState.isScrollInProgress) {
                    if (listState.isHalfPastItemLeft())
                        coroutineScope.scrollBasic(listState, left = true)
                    else
                        coroutineScope.scrollBasic(listState)

                    if (listState.isHalfPastItemRight())
                        coroutineScope.scrollBasic(listState)
                    else
                        coroutineScope.scrollBasic(listState, left = true)
                }
            }
            articles.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { LoadingProgressBar() }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { LoadingProgressBar() }
                    }
                    loadState.refresh is LoadState.Error -> {
                        item { ShowErrorOrDialog(loadState.refresh as LoadState.Error) }
                    }
                    loadState.append is LoadState.Error -> {
                        item { ShowErrorOrDialog(loadState.append as LoadState.Error) }
                    }
                }
            }

        })


}

//TODO: Make extensions file and import
private fun CoroutineScope.scrollBasic(listState: LazyListState, left: Boolean = false) {
    launch {
        val pos = if (left) listState.firstVisibleItemIndex else listState.firstVisibleItemIndex + 1
        listState.animateScrollToItem(pos)
    }
}

@Composable
private fun LazyListState.isHalfPastItemRight(): Boolean {
    return firstVisibleItemScrollOffset > HALF_PAST_ITEM_RIGHT
}

@Composable
private fun LazyListState.isHalfPastItemLeft(): Boolean {
    return firstVisibleItemScrollOffset <= HALF_PAST_ITEM_LEFT
}