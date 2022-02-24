package com.wizeline.mobilenews.domain.di

import com.wizeline.mobilenews.data.repo.NetworkRepository
import com.wizeline.mobilenews.domain.repositories.CommunityRepository
import com.wizeline.mobilenews.domain.repositories.NewsRepository
import com.wizeline.mobilenews.domain.usecases.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@InstallIn(SingletonComponent::class)
@Module
abstract class AppModule {

    @Binds
    abstract fun bindNetworkRepository(networkRepository: NetworkRepository): NewsRepository

    @Binds
    abstract fun bindCommunityRepository(
        communityRepository: CommunityRepository //TODO: Change for repo implementation
    ): CommunityRepository

    @Binds
    abstract fun bindSearchNewsUseCase(
        searchNewsUseCase: SearchNewsUseCaseImpl
    ): SearchNewsUseCase

    @Binds
    abstract fun bindGetCommunityNewsUseCase(
        getCommunityNewsUseCaseImpl: GetCommunityNewsUseCaseImpl
    ): GetCommunityNewsUseCase

    @Binds
    abstract fun bindSaveCommunityArticleUseCase(
        saveCommunityArticleUseCaseImpl: SaveCommunityArticleUseCaseImpl
    ): SaveCommunityArticleUseCase
}