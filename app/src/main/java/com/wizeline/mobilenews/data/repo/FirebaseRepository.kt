package com.wizeline.mobilenews.data.repo

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.wizeline.mobilenews.data.db.firebase.FirebaseFirestoreManager
import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.domain.data_source.FirestorePagingSource
import com.wizeline.mobilenews.domain.models.CommunityArticle
import com.wizeline.mobilenews.domain.repositories.CommunityRepository
import javax.inject.Inject

class FirebaseRepository @Inject constructor(
    private val firebaseFirestoreManager: FirebaseFirestoreManager,
    private val datasource: FirestorePagingSource,
    private val config: PagingConfig
) : CommunityRepository {
    override suspend fun searchNews(
        query: List<String>,
        dateFrom: Long,
        dateTo: Long?,
        pageSize: Int,
        page: Int,
    ): List<CommunityArticle> =
        firebaseFirestoreManager.searchCommunityArticles(query, dateFrom, dateTo, page, pageSize)

    override suspend fun saveArticle(
        communityArticle: CommunityArticle,
        imageUri: Uri
    ): NetworkResults<String?> =
        firebaseFirestoreManager.createArticle(communityArticle, imageUri)

    override fun getArticles(): LiveData<PagingData<CommunityArticle>> {
        return Pager(
            config = config
        ) {
            datasource
        }.liveData
    }
}