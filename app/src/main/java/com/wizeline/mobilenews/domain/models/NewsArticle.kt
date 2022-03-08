package com.wizeline.mobilenews.domain.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class NewsArticle(
    val title: String?,
    val author: String?,
    @SerializedName("published_date")
    val publishedDate: String?,
    val link: String?,
    val excerpt: String?,
    val summary: String?,
    @SerializedName("media")
    val image: String?
): Serializable