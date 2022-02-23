package com.wizeline.mobilenews.data.db.firebase

import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.Timestamp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseFirestoreManager(private val collectionName: String) {

    companion object {
        private val TAG = "FirestoreManager"
    }

    init {
        getFirestoreDbCollection(collectionName)
    }

    private fun getFirestoreDbCollection(collectionName: String): CollectionReference {
        return Firebase.firestore.collection(collectionName)
    }

    suspend fun create(
        article: Any /* TODO: Change to article type implementation*/
    ): String = suspendCoroutine { cor ->
        // Create a hash map with the article information
        val communityArticle = hashMapOf(
            "author" to "Poncho",
            "image_url" to "www.example.com",
            "published_date" to Timestamp.now(),
            "text" to "this is the article content",
            "title" to "title"
        )

        getFirestoreDbCollection(collectionName)
            .add(communityArticle)
            .addOnSuccessListener {
                cor.resume(it.id)
            }
            .addOnFailureListener {
                cor.resume("")
                Log.e(TAG, "An error occurred during the creation of a new document")
                it.printStackTrace()
            }
    }


    suspend fun getAllCommunityArticles(): QuerySnapshot? = suspendCoroutine {cor ->
        getFirestoreDbCollection(collectionName)
            .get()
            .addOnSuccessListener {
                cor.resume(it)
            }
            .addOnFailureListener {
                cor.resume(null)
                Log.e(TAG, "An error occurred while getting the document")
                it.printStackTrace()
            }
    }
}