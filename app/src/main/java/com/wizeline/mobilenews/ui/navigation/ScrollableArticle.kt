package com.wizeline.mobilenews.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.wizeline.mobilenews.*
import com.wizeline.mobilenews.R
import com.wizeline.mobilenews.domain.models.Article

@Composable
fun ScrollableArticle(article: Article) {
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val imageHeightPercentage = ARTICLE_IMG_HEIGHT
    val remainingTitleHeight = screenHeight/imageHeightPercentage
    Column(
        Modifier
            .verticalScroll(rememberScrollState())
            .background(Color.Black)
    ) {
        NewsImage(
            article = article,
            modifier = Modifier
                .height(screenHeight - screenHeight / imageHeightPercentage)
        )
        Text(
            text = article.title,
            color = Color.Gray,
            textAlign = TextAlign.Center,
            fontSize = fontDimensionResource(R.dimen.title_size),
            modifier = Modifier
                .padding(
                    top = dimensionResource(R.dimen.padding_30),
                    start = dimensionResource(R.dimen.default_padding),
                    end = dimensionResource(R.dimen.default_padding)
                )
                .height(
                    remainingTitleHeight - dimensionResource(R.dimen.padding_30)
                )
        )
        ConstraintLayout(Modifier.fillMaxWidth()) {
            val author = createRef()
            Text(
                text = article.author ?: EMPTY_STR,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.subtitle1,
                modifier = Modifier
                    .constrainAs(author) {
                        end.linkTo(parent.end)
                    }
                    .padding(
                        end = dimensionResource(R.dimen.default_padding)
                    )
            )
        }
        Text(
            text = article.text,
            color = Color.Gray,
            textAlign = TextAlign.Justify,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier
                .padding(
                    top = dimensionResource(R.dimen.padding_30),
                    start = dimensionResource(R.dimen.default_padding),
                    end = dimensionResource(R.dimen.padding_30))
        )

        Spacer(modifier = Modifier.height(dimensionResource(R.dimen.spacer_height)))
    }
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun NewsImage(article: Article, modifier: Modifier = Modifier) {
    var articleImg = article.image
    if(REGEX_IMG.toRegex() !in articleImg){
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
        contentScale = ContentScale.FillHeight,
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