package com.wizeline.mobilenews.domain.usecases

import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.domain.models.CommunityArticle

interface SaveCommunityArticleUseCase {
    suspend operator fun invoke(communityArticle: CommunityArticle): NetworkResults<String?>
}