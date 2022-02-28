package com.wizeline.mobilenews.data.models

import com.wizeline.mobilenews.domain.models.CommunityArticle

data class CommunityNewsRaw(
    val articles: List<CommunityArticle> //TODO: Change Firebase database or adapt model
)