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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.rememberImagePainter
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.ui.theme.Percent50Light
import com.wizeline.mobilenews.ui.theme.Typography
import com.wizeline.mobilenews.ui.theme.percent20Light

@Composable
fun ArticleListItem(article: Article) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(250.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (image, bottom_info, title, author) = createRefs()
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
                .blur(radius = 150.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Percent50Light,
                            percent20Light
                        )
                    )
                )
                .height(50.dp)
                .constrainAs(bottom_info) {
                    bottom.linkTo(parent.bottom)
                }){
               Text(text = article.title,
                   style = Typography.h2,
                   modifier = Modifier
                       .padding(8.dp)
                       .constrainAs(title) {
                           top.linkTo(parent.top)
                       })
           }

            Text(
                text = article.author ?: "",
                style = Typography.h2,
                modifier = Modifier
                    .padding(8.dp)
                    .constrainAs(author) {
                        top.linkTo(title.bottom)
                        end.linkTo(parent.end)
                    }
            )
        }

    }
}

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