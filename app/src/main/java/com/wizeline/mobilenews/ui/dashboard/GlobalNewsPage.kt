package com.wizeline.mobilenews.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.wizeline.mobilenews.*
import com.wizeline.mobilenews.R
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.ui.theme.Typography
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun GlobalNewsScreen() {
    val viewModel: GlobalNewsViewModel = hiltViewModel()
    val articles = viewModel.getArticles().asFlow()
    val lazyArticles = articles.collectAsLazyPagingItems()
    LazyRow(modifier = Modifier.fillMaxSize(),
        content = {
            items(lazyArticles.itemCount) { index ->
                lazyArticles[index]?.let { article -> CustomScrollableArticle(article) }
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

@OptIn(ExperimentalCoilApi::class)
@Composable
fun NewsImage(article: Article, modifier: Modifier = Modifier) {
    var articleImg = article.image
    if (REGEX_IMG.toRegex() !in articleImg) {
        articleImg = DEFAULT_ARTICLE_IMG
    }
    Image(
        painter = rememberImagePainter(
            data = articleImg,
            builder = {
                placeholder(R.drawable.ic_launcher_background) //Todo: Change placeholder to loading
            }
        ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .fillMaxWidth()
            .drawWithCache {
                val gradient = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black),
                    startY = size.height - (size.height / ARTICLE_IMG_GRADIENT_HEIGHT),
                    endY = size.height
                )
                onDrawWithContent {
                    drawContent()
                    drawRect(gradient, blendMode = BlendMode.Multiply)
                }
            }

    )
}