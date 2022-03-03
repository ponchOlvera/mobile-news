package com.wizeline.mobilenews.domain.extensions

import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.domain.models.CommunityArticle
import com.wizeline.mobilenews.domain.models.NewsArticle


fun NewsArticle.toArticle() = Article(
    title,
    author,
    publishedDate,
    image,
    summary,
    link
)

fun CommunityArticle.toArticle() = Article(
    title,
    author,
    publishedDate.toString(),
    imageUrl,
    text
)