package com.wizeline.mobilenews.domain.models

import java.io.Serializable

data class CommunityArticle(
    val title: String,
    val author: String?,
    val publishedDate: String,
    val image: String,
    val summary: String,
): Serializable