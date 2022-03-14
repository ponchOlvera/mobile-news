package com.wizeline.mobilenews.ui.createArticle

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wizeline.mobilenews.EMPTY_STRING
import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.domain.models.CommunityArticle
import com.wizeline.mobilenews.domain.usecases.SaveCommunityArticleUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreateArticleViewModel @Inject constructor(val saveCommunityArticleUseCase: SaveCommunityArticleUseCase) :
    ViewModel() {

    private val authorNameRegex = "^(?![ .]+$)[a-zA-ZÀ-ú .\n]*$".toRegex()

    var articleTitle: String by mutableStateOf(EMPTY_STRING)
        private set
    var articleAuthor: String by mutableStateOf(EMPTY_STRING)
        private set
    var articleDescription: String by mutableStateOf(EMPTY_STRING)
        private set
    var imageUri: Uri? by mutableStateOf(Uri.EMPTY)
        private set
    var uiState: UiState by mutableStateOf(UiState.NoState)
        private set

    suspend fun createCommunityPost() {
        val formComplete = allFieldsCorrect()

        if (formComplete is UiState.MissingFieldAction) {
            uiState = formComplete
            return
        }

        val communityArticle = CommunityArticle(
            title = articleTitle,
            author = articleAuthor,
            text = articleDescription,
            publishedDate = System.currentTimeMillis(),
        )

        viewModelScope.launch(Dispatchers.IO) {
            uiState = UiState.LoadingState
            val response =
                saveCommunityArticleUseCase(communityArticle, imageUri ?: Uri.EMPTY)
            if (response is NetworkResults.Success) {
                uiState = UiState.RequestCompleted("Post successfully created!")
                return@launch
            }
        }
    }

    private fun allFieldsCorrect(): UiState {
        return if (articleTitle.trim().isEmpty()) {
            UiState.MissingFieldAction("The post title should not be empty")
        } else if (articleAuthor.trim().isEmpty()) {
            UiState.MissingFieldAction("The post author should not be empty")
        } else if (!authorNameRegex.matches(articleAuthor)) {
            UiState.MissingFieldAction("The post author is invalid, no special characters nor numbers allowed")
        } else if (articleDescription.trim().isEmpty()) {
            UiState.MissingFieldAction("The post description should not be empty")
        } else if (imageUri.toString().trim().isEmpty()) {
            UiState.MissingFieldAction("You have to select an image")
        } else {
            UiState.NoState
        }
    }

    sealed class UiState {
        object NoState : UiState()
        object LoadingState : UiState()
        data class MissingFieldAction(
            val message: String = EMPTY_STRING
        ) : UiState()

        data class RequestCompleted(val message: String) : UiState()
    }

    fun updateArticleTitle(newTitle: String) {
        this.articleTitle = newTitle
    }

    fun updateArticleAuthor(newAuthor: String) {
        this.articleAuthor = newAuthor
    }

    fun updateArticleDescription(newDescription: String) {
        this.articleDescription = newDescription
    }

    fun updateImageUri(newImageUri: Uri) {
        imageUri = newImageUri
    }

    fun restartUiState() {
        uiState = UiState.NoState
    }

}