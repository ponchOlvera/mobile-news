package com.wizeline.mobilenews.domain.models

import com.wizeline.mobilenews.EMPTY_STRING
import java.io.Serializable

data class CommunityArticle(
    val title: String = EMPTY_STRING,
    val author: String = EMPTY_STRING,
    val publishedDate: Long? = null,
    val tags: List<String> = listOf(),
    val imageUrl: String = EMPTY_STRING,
    val text: String = EMPTY_STRING,
): Serializable
