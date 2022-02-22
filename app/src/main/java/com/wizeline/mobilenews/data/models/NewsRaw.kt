package com.wizeline.mobilenews.data.models

import com.google.gson.annotations.SerializedName

data class NewsRaw(
    val status: String,
    @SerializedName("total_hits")
    val totalHits: String,
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("page_size")
    val pageSize: Int,
    val articles: Any, /* TODO: Replace it with a list of articles (List<Article>)*/
)
