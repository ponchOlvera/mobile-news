package com.wizeline.mobilenews.domain.extensions

import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.domain.models.CommunityArticle
import com.wizeline.mobilenews.domain.models.NewsArticle


fun NewsArticle.toArticle() = Article(
    title.orEmpty(),
    author,
    publishedDate.orEmpty(),
    image.orEmpty(),
    summary.orEmpty(),
    link
)

fun CommunityArticle.toArticle() = Article(
    title,
    author,
    publishedDate.toString(),
    imageUrl,
    text
)

fun Int?.orOne() = this ?: 1

fun List<NewsArticle>?.orEmpty() = this ?: emptyList()
