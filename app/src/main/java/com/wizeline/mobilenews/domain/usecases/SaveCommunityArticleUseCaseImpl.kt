package com.wizeline.mobilenews.domain.usecases

import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.domain.models.CommunityArticle
import com.wizeline.mobilenews.domain.repositories.CommunityRepository
import javax.inject.Inject

class SaveCommunityArticleUseCaseImpl @Inject constructor(
    private val communityRepository: CommunityRepository
) : SaveCommunityArticleUseCase {
    override suspend fun invoke(communityArticle: CommunityArticle): NetworkResults<String?> =
        communityRepository.saveArticle(communityArticle)
}