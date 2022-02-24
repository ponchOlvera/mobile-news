package com.wizeline.mobilenews.domain.usecases

import com.wizeline.mobilenews.domain.models.CommunityArticle
import com.wizeline.mobilenews.domain.repositories.CommunityRepository

class SaveCommunityArticleUseCaseImpl(
    private val communityRepository: CommunityRepository
): SaveCommunityArticleUseCase {
    override suspend fun invoke(communityArticle: CommunityArticle) {
        communityRepository.saveArticle(communityArticle)
    }
}