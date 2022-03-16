package com.wizeline.mobilenews.domain.data_source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import com.wizeline.mobilenews.PAGE_SIZE
import com.wizeline.mobilenews.PAGE_SIZE_COMMUNITY
import com.wizeline.mobilenews.data.db.firebase.FirebaseFirestoreManager
import com.wizeline.mobilenews.domain.models.CommunityArticle
import kotlinx.coroutines.tasks.await


class FirestorePagingSource (
//    private val query: Query,
    private val firestoreManager: FirebaseFirestoreManager,
) : PagingSource<QuerySnapshot, CommunityArticle>() {
    override fun getRefreshKey(state: PagingState<QuerySnapshot, CommunityArticle>): QuerySnapshot? = null

    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, CommunityArticle> {
        return try {
            val query = firestoreManager.getAllCommunityArticlesQuery(PAGE_SIZE_COMMUNITY.toLong())
            val currentPage = params.key ?: query.get().await()
            val lastVisibleArticle = currentPage.documents[currentPage.size() - 1]
            val nextPage = query.startAfter(lastVisibleArticle).get().await()
            println("Current Page: ${lastVisibleArticle.data}")
            LoadResult.Page(
                data = currentPage.toObjects(CommunityArticle::class.java),
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

//class FirestorePagingSource (
//    private val queryProductsByName: Query
//) : PagingSource<QuerySnapshot, CommunityArticle>() {
//    override fun getRefreshKey(state: PagingState<QuerySnapshot, CommunityArticle>): QuerySnapshot? = null
//
//    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, CommunityArticle> {
//        return try {
//            val currentPage = params.key ?: queryProductsByName.get().await()
//            val lastVisibleProduct = currentPage.documents[currentPage.size() - 1]
//            val nextPage = queryProductsByName.startAfter(lastVisibleProduct).get().await()
//            LoadResult.Page(
//                data = currentPage.toObjects(CommunityArticle::class.java),
//                prevKey = null,
//                nextKey = nextPage
//            )
//        } catch (e: Exception) {
//            LoadResult.Error(e)
//        }
//    }
//}