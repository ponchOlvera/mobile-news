package com.wizeline.mobilenews.domain.usecases

import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.domain.models.Article

interface GetCommunityNewsUseCase {
    suspend operator fun invoke(
        dateFrom: String?,
        dateTo: String?,
        pageSize: Int?,
        page: Int?
    ): NetworkResults<List<Article>>
}