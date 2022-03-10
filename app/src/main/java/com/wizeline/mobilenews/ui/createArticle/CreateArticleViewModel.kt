package com.wizeline.mobilenews.ui.createArticle

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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

    val articleName: MutableState<String> = mutableStateOf(EMPTY_STRING)
    val articleAuthor: MutableState<String> = mutableStateOf(EMPTY_STRING)
    val articleDescription: MutableState<String> = mutableStateOf(EMPTY_STRING)
    val imageUri: MutableState<Uri?> = mutableStateOf(Uri.EMPTY)
    val uiState: MutableState<UiState> = mutableStateOf(UiState.NoState)

    suspend fun createCommunityPost() {
        val formComplete = allFieldsCorrect()

        if (formComplete is UiState.MissingFieldAction) {
            uiState.value = formComplete
            return
        }

        val communityArticle = CommunityArticle(
            title = articleName.value,
            author = articleAuthor.value,
            text = articleDescription.value,
            publishedDate = System.currentTimeMillis(),
        )

        viewModelScope.launch(Dispatchers.IO) {
            uiState.value = UiState.LoadingState
            val response =
                saveCommunityArticleUseCase(communityArticle, imageUri.value ?: Uri.EMPTY)
            if (response is NetworkResults.Success) {
                uiState.value = UiState.RequestCompleted("Post successfully created!")
                return@launch
            }
        }

        //Upload image to firebase
        Log.i("CreateViewModel", communityArticle.toString())
    }

    private fun allFieldsCorrect(): UiState {
        Log.i("CreateViewModel", imageUri.value.toString().trim())
        return if (articleName.value.trim().isEmpty()) {
            UiState.MissingFieldAction("The post title should not be empty")
        } else if (articleAuthor.value.trim().isEmpty()) {
            UiState.MissingFieldAction("The post author should not be empty")
        } else if (!authorNameRegex.matches(articleAuthor.value)) {
            UiState.MissingFieldAction("The post author is invalid, no special characters nor numbers allowed")
        } else if (articleDescription.value.trim().isEmpty()) {
            UiState.MissingFieldAction("The post description should not be empty")
        } else if (imageUri.value.toString().trim().isEmpty()) {
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

}