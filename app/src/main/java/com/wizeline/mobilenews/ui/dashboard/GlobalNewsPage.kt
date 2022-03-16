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

    LazyRow(
        state = listState,
        modifier = Modifier.fillMaxSize(),
        content = {
            items(lazyArticles.itemCount) { index ->
                lazyArticles[index]?.let { article -> CustomScrollableArticle(article) }

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
            lazyArticles.apply {
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

@Composable
fun CustomScrollableArticle(article: Article) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp
    val screenHeight = configuration.screenHeightDp
    ConstraintLayout(
        modifier = Modifier
            .padding(8.dp)
            .width(screenWidth.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState()),
    ) {
        val (image, title, author, detail) = createRefs()
        NewsImage(
            article = article,
            modifier = Modifier
                .width(screenWidth.dp)
                .height((screenHeight * 0.3).dp)
                .constrainAs(image) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                }
        )
        Text(
            text = article.title,
            textAlign = TextAlign.Center,
            style = Typography.h1,
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(title) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(image.bottom)
                }
        )
        Text(
            text = article.author ?: EMPTY_STR,
            textAlign = TextAlign.Center,
            style = Typography.body1,
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(author) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(title.bottom)
                }
        )
        Text(
            text = article.text,
            textAlign = TextAlign.Justify,
            style = Typography.body1,
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(detail) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(author.bottom)
                }
        )
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
