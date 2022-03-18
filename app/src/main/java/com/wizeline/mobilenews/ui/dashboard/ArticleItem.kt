package com.wizeline.mobilenews.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.constraintlayout.compose.ConstraintLayout
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.wizeline.mobilenews.R
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.ui.theme.GrayTransparent
import com.wizeline.mobilenews.ui.theme.Typography

@Composable
fun ArticleItem(article: Article, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.search_article_item_height)),
        elevation = dimensionResource(R.dimen.card_elevation),
        shape = RoundedCornerShape(corner = CornerSize(dimensionResource(R.dimen.corner_radius)))
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (image, bottom_info) = createRefs()
            Box(modifier = Modifier
                .fillMaxSize()
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    end.linkTo(parent.end)
                }) {
                ArticleImage(article = article)
            }
            ConstraintLayout(modifier = Modifier
                .blur(radius = dimensionResource(R.dimen.search_article_title_height))
                .background(GrayTransparent)
                .fillMaxWidth()
                .height(dimensionResource(R.dimen.search_article_title_bg_height))
                .constrainAs(bottom_info) {
                    bottom.linkTo(parent.bottom)
                }) {
                val (title, author) = createRefs()
                Text(text = article.title,
                    style = Typography.h4,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_small))
                        .constrainAs(title) {
                            top.linkTo(parent.top)
                        })

                Text(
                    text = article.author.orEmpty(),
                    style = Typography.h4,
                    modifier = Modifier
                        .padding(dimensionResource(R.dimen.padding_small))
                        .constrainAs(author) {
                            top.linkTo(title.bottom)
                            end.linkTo(parent.end)
                        }
                )
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
fun ArticleImage(article: Article) {
    Image(
        painter = rememberImagePainter(article.image),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    )
}