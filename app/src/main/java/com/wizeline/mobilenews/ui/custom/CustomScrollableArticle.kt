package com.wizeline.mobilenews.ui.custom

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
fun CustomScrollableArticle(article: Article?) {
    if (article == null) return
    val configuration = LocalConfiguration.current
    val screenHeight = configuration.screenHeightDp.dp
    val screenWidth = configuration.screenWidthDp.dp
    val imageHeightPercentage = ARTICLE_IMG_HEIGHT
    val remainingTitleHeight = screenHeight/imageHeightPercentage
    Box(
        Modifier
            .width(screenWidth)
            .verticalScroll(rememberScrollState())
    ) {
        NewsImage(
            article = article,
            modifier = Modifier
                .height(screenHeight - screenHeight / imageHeightPercentage)
        )

        Card(
            elevation = dimensionResource(R.dimen.card_elevation),
            shape = RoundedCornerShape(
                topStart = dimensionResource(R.dimen.corner_radius),
                topEnd= dimensionResource(R.dimen.corner_radius),
            ),
            backgroundColor = MaterialTheme.colors.background,
            modifier = Modifier.padding(
                top =
                screenHeight -(remainingTitleHeight + dimensionResource(R.dimen.corner_radius))
            ),
        ){
            Column {
                Text(
                    text = article.title,
                    textAlign = TextAlign.Center,
                    fontSize = fontDimensionResource(R.dimen.title_size),
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(R.dimen.padding_30),
                            start = dimensionResource(R.dimen.default_padding),
                            end = dimensionResource(R.dimen.default_padding)
                        )
                        .height(remainingTitleHeight)
                )
                ConstraintLayout(Modifier.fillMaxWidth()) {
                    val author = createRef()
                    Text(
                        text = article.author ?: EMPTY_STRING,
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
    )
}