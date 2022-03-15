package com.wizeline.mobilenews.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Article(
    val title: String,
    val author: String?,
    val date: String,
    val image: String,
    val text: String,
    val link: String? = null
): Parcelable