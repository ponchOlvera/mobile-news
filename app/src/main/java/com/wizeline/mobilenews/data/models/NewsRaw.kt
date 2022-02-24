package com.wizeline.mobilenews.data.models

import com.google.gson.annotations.SerializedName
import com.wizeline.mobilenews.domain.models.NewsArticle

data class NewsRaw(
    val status: String,
    @SerializedName("total_hits")
    val totalHits: String,
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("page_size")
    val pageSize: Int,
    val articles: List<NewsArticle>
)
