package com.wizeline.mobilenews.domain.models

data class Article(
    val title: String,
    val author: String?,
    val date: String,
    val image: String,
    val text: String,
    val link: String? = null
)