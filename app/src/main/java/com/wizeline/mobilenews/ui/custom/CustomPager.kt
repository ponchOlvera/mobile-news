package com.wizeline.mobilenews.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.pager.ExperimentalPagerApi
import com.wizeline.mobilenews.EMPTY_STR
import com.wizeline.mobilenews.R
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.ui.dashboard.LoadingItem
import com.wizeline.mobilenews.ui.navigation.NewsImage
import com.wizeline.mobilenews.ui.theme.Typography
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalPagerApi::class)
@Composable
fun CustomArticlesPager(articles: Flow<PagingData<Article>>) {
    val lazyArticles = articles.collectAsLazyPagingItems()
    LazyRow(modifier = Modifier.fillMaxSize(),
        content = {
            items(lazyArticles.itemCount) { index ->
                lazyArticles[index]?.let { article -> CustomScrollableArticle(article) }
            }
            lazyArticles.apply {
                when {
                    loadState.refresh is
                            LoadState.Loading -> {
                        item { LoadingItem() }
                    }
                    loadState.append is
                            LoadState.Loading -> {
                        item { LoadingItem() }
                        item { LoadingItem() }
                    }
                    loadState.refresh is
                            LoadState.Error -> {
                        item { LoadingItem() }
                        item { LoadingItem() }
                    }
                    loadState.append is
                            LoadState.Error -> {
                        item { LoadingItem() }
                        item { LoadingItem() }
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
            .verticalScroll(rememberScrollState())
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
            color = Color.LightGray,
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
            color = Color.LightGray,
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
            color = Color.LightGray,
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
