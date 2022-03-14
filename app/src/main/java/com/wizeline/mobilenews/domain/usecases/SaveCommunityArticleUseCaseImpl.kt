package com.wizeline.mobilenews.domain.usecases

import android.net.Uri
import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.domain.models.CommunityArticle
import com.wizeline.mobilenews.domain.repositories.CommunityRepository
import javax.inject.Inject

class SaveCommunityArticleUseCaseImpl @Inject constructor(
    private val communityRepository: CommunityRepository
) : SaveCommunityArticleUseCase {
    override suspend fun invoke(
        communityArticle: CommunityArticle,
        imageUri: Uri
    ): NetworkResults<String?> =
        communityRepository.saveArticle(communityArticle, imageUri)
}