package com.wizeline.mobilenews.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
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
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.wizeline.mobilenews.domain.models.Article

@Composable
fun ScrollableArticle(article: Article) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val imageHeightPercentage = 4
    val remainingTitleHeight = screenHeight/imageHeightPercentage
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .background(Color.Black)
    ) {
        NewsImage(
            article = article,
            modifier = Modifier.height(screenHeight - screenHeight / imageHeightPercentage) //Ocupy 3/4 of screen size
        )
        Text(
            text = article.title,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
            modifier = Modifier
                .padding(
                    top = 30.dp,
                    start = 10.dp, end = 10.dp)
                .height(remainingTitleHeight-30.dp)
        )
        ConstraintLayout(Modifier.fillMaxWidth()) {
            val author = createRef()
            Text(
                text = article.author ?: "",
                color = Color.Gray,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .constrainAs(author){
                        end.linkTo(parent.end)
                    }
                    .padding(end = 10.dp)
            )
        }
        Text(
            text = article.text,
            color = Color.Gray,
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(top = 20.dp, start = 10.dp, end = 10.dp)
        )
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun NewsImage(article: Article, modifier: Modifier = Modifier) {
    Image(
        painter = rememberImagePainter(article.image),
        contentDescription = null,
        contentScale = ContentScale.FillHeight,
        modifier = modifier
            .fillMaxWidth()
//            .height(screenHeight - screenHeight / 4) //Ocupy 3/4 of screen size
            .drawWithCache {
                val gradient = Brush.verticalGradient(
                    colors = listOf(Color.Transparent, Color.Black),
                    startY = size.height - (size.height / 6),
                    endY = size.height
                )
                onDrawWithContent {
                    drawContent()
                    drawRect(gradient, blendMode = BlendMode.Multiply)
                }
            }

    )
}