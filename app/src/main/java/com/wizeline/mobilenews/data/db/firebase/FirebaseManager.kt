package com.wizeline.mobilenews.data.db.firebase

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.domain.models.CommunityArticle
import com.wizeline.mobilenews.domain.models.Tag
import com.wizeline.mobilenews.toFormattedDateString
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseFirestoreManager(private val collectionName: String) {

    private val TAG = "FirestoreManager"
    private var searchQueryList: List<String> = emptyList()
    private var lastVisibleDocument: DocumentSnapshot? = null
    private lateinit var imageReference: StorageReference

    init {
        getFirestoreDbCollection(collectionName)
    }

    private fun getFirestoreDbCollection(collectionName: String): CollectionReference {
        return Firebase.firestore.collection(collectionName)
    }

    suspend fun createArticle(
        article: CommunityArticle,
        imageUri: Uri
    ): NetworkResults<String?> = suspendCoroutine { cor ->

        saveImageInCloudFirestore(imageUri, article.publishedDate)
            .addOnSuccessListener { task ->
                imageReference.downloadUrl
                    .addOnSuccessListener { downloadUrl ->

                        Log.i(TAG, "${downloadUrl}")
                        // Create a hash map with the article information
                        val communityArticleHashMap = hashMapOf(
                            AUTHOR to article.author,
                            IMAGE_URL to downloadUrl.toString(),
                            PUBLISHED_DATE to article.publishedDate,
                            TAGS to article.tags,
                            TEXT to article.text,
                            TITLE to article.title
                        )

                        var tagList: List<Tag> = emptyList()

                        getTagsCollection { list ->
                            tagList = list
                            Log.i(TAG, "TagList: $tagList")
                            article.tags.forEach {
                                val tag = Tag(it)
                                if (!tagList.contains(tag)) {
                                    addTagToTagsCollection(tag)
                                }
                            }
                        }

                        getFirestoreDbCollection(collectionName)
                            .add(communityArticleHashMap)
                            .addOnSuccessListener {
                                cor.resume(NetworkResults.Success(it.id))
                            }
                            .addOnFailureListener {
                                val errorMessage =
                                    "An error occurred during the creation of a new document"
                                cor.resume(NetworkResults.Error(errorMessage))
                                Log.e(TAG, errorMessage)
                                it.printStackTrace()
                            }
                        Log.i(TAG, tagList.toString())
                    }
                    .addOnCanceledListener {

                    }


            }
            .addOnFailureListener {
                val errorMessage = "The image could not be saved."
                cor.resume(NetworkResults.Error(errorMessage))
                Log.e(TAG, errorMessage)
                it.printStackTrace()
            }

    }

    private fun saveImageInCloudFirestore(imageUri: Uri, publishedDate: Long?): UploadTask {
        val storageReference = Firebase.storage(STORAGE_REFERENCE)
        val imageName = publishedDate?.toFormattedDateString()
        imageReference = storageReference.getReference("$STORAGE_FOLDER$imageName.jpg")
        return imageReference.putFile(imageUri)
    }

    private fun getTagsCollection(callback: (List<Tag>) -> Unit) {
        val tagsDbReference = getFirestoreDbCollection(TAGS_COLLECTION)
        tagsDbReference.get()
            .addOnSuccessListener { tagsCollection ->
                val tagList = tagsCollection.toObjects(Tag::class.java)
                callback(tagList)
            }
            .addOnFailureListener {
                it.printStackTrace()
                callback(emptyList())
            }
    }

    private fun addTagToTagsCollection(tag: Tag) {
        getFirestoreDbCollection(TAGS_COLLECTION).add(tag)
            .addOnSuccessListener {
                Log.i(TAG, "OnSuccessListener(): ${it.id}")
            }
            .addOnFailureListener {
                Log.i(TAG, "OnFailureListener(): ${it.message}")
            }
    }

    suspend fun searchCommunityArticles(
        query: List<String> = emptyList(),
        dateFrom: Long,
        dateTo: Long?,
        page: Int = 1,
        pageSize: Int = 20
    ): List<CommunityArticle> = suspendCoroutine { cor ->

        if (query != searchQueryList) {
            searchQueryList = query
            lastVisibleDocument = null
        }

        val searchRequest = getFirestoreDbCollection(collectionName)

        val searchQuery: Query = if (searchQueryList.isNotEmpty()) {
            searchRequest
                .whereArrayContainsAny(TAGS, searchQueryList)
                .orderBy(PUBLISHED_DATE, Query.Direction.DESCENDING)
                .limit(pageSize.toLong())
        } else {
            searchRequest
                .orderBy(PUBLISHED_DATE, Query.Direction.DESCENDING)
                .limit(pageSize.toLong())
        }

        searchQuery.get(Source.SERVER)
            .addOnSuccessListener {
                val documentSize = it.documents.size
                if (documentSize > 0) {
                    lastVisibleDocument = it.documents[it.size() - 1]
                    val articleList = it.toObjects(CommunityArticle::class.java)
                    cor.resume(articleList)
                }
                Log.i(TAG, "${it.documents.size}")
            }
            .addOnFailureListener {
                cor.resume(emptyList())
                Log.e(TAG, "An error occurred while searching the community articles")
                it.printStackTrace()
            }
    }

    companion object {
        const val ARTICLE_COLLECTION = "CommunityArticles"
        const val TAGS_COLLECTION = "Tags"

        //Firebase fields
        const val AUTHOR = "author"
        const val IMAGE_URL = "imageUrl"
        const val PUBLISHED_DATE = "publishedDate"
        const val TAGS = "tags"
        const val TEXT = "text"
        const val TITLE = "title"
        const val TITLE_LOWER_CASE = "titleLowerCase"

        //Cloud Firestore
        const val STORAGE_REFERENCE = "gs://mobile-news-d6b31.appspot.com"
        const val STORAGE_FOLDER = "images/"
    }
}