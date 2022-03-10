package com.wizeline.mobilenews.domain.usecases

import android.net.Uri
import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.domain.models.CommunityArticle

interface SaveCommunityArticleUseCase {
    suspend operator fun invoke(
        communityArticle: CommunityArticle,
        imageUri: Uri
    ): NetworkResults<String?>
}