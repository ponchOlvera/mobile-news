package com.wizeline.mobilenews.ui.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.wizeline.mobilenews.HALF_PAST_ITEM_LEFT
import com.wizeline.mobilenews.HALF_PAST_ITEM_RIGHT
import com.wizeline.mobilenews.ui.custom.CustomScrollableArticle
import com.wizeline.mobilenews.ui.custom.LoadingItem
import com.wizeline.mobilenews.ui.custom.ShowErrorOrDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@Composable
fun GlobalNewsPage() {
    val viewModel: GlobalNewsViewModel = hiltViewModel()
    val articles = viewModel.getArticles().asFlow()
    val lazyArticles = articles.collectAsLazyPagingItems()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LazyRow(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        content = {
            items(lazyArticles.itemCount) { index ->
                lazyArticles[index]?.let { article -> CustomScrollableArticle(article) }

                if(!listState.isScrollInProgress){
                    if(listState.isHalfPastItemLeft())
                        coroutineScope.scrollBasic(listState, left = true)
                    else
                        coroutineScope.scrollBasic(listState)

                    if(listState.isHalfPastItemRight())
                        coroutineScope.scrollBasic(listState)
                    else
                        coroutineScope.scrollBasic(listState, left = true)
                }
            }
            lazyArticles.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { LoadingItem() }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { LoadingItem() }
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
private fun CoroutineScope.scrollBasic(listState: LazyListState, left: Boolean = false){
    launch {
        val pos = if(left) listState.firstVisibleItemIndex else listState.firstVisibleItemIndex+1
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
