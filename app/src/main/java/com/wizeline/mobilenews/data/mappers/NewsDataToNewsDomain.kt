package com.wizeline.mobilenews.data.mappers

import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.data.models.NewsRaw
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.domain.models.CommunityArticle
import com.wizeline.mobilenews.domain.models.NewsArticle

abstract class NewsDataToNewsDomain<T, R> {

    fun mapNetworkResult(
        items: List<T>?,
        mapTo: (T) -> R,
    ): NetworkResults<List<R>> {
        return try {
            if(items.isNullOrEmpty())
                error("Null list")
            else{
                val mappedItems = items.map(mapTo)
                NetworkResults.Success(mappedItems)
            }
        } catch (e: Exception) {
            error(e.message ?: e.toString())
        }
    }
    private fun <T> error(errorMessage: String): NetworkResults<T> =
        NetworkResults.Error("Mapping failed: $errorMessage")

}
