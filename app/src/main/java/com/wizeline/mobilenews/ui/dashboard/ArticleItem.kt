package com.wizeline.mobilenews.ui.dashboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Composable
fun ArticleListItem(article: DummyArticle, navigateToDetail: (DummyArticle) -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .height(250.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(16.dp))
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .clickable { navigateToDetail(article) }) {
            ArticleImage(article = article)
            Text(text = article.title, modifier = Modifier.padding(8.dp))
            Text(text = article.excerpt, modifier = Modifier.padding(8.dp))
        }
    }
}

@Composable
fun ArticleImage(article: DummyArticle) {
    Image(
        painter = rememberImagePainter("https://ewscripps.brightspotcdn.com/dims4/default/d47c692/2147483647/strip/true/crop/1280x672+0+144/resize/1200x630!/quality/90/?url=http%3A%2F%2Fewscripps-brightspot.s3.amazonaws.com%2F6f%2F91%2Ff935dc0440e595450ceb0d4d511f%2Fconstruction-accident.jpg"),
        contentDescription = null,
        contentScale = ContentScale.FillWidth,
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
    )
}

@Preview
@Composable
fun PreviewArticleItem() {
    ArticleListItem(article = DummyArticle(), navigateToDetail = {})
}