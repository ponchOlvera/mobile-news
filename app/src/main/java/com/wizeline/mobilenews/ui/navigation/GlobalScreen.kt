package com.wizeline.mobilenews.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.ui.dashboard.ArticleViewModel

@Composable
fun GlobalScreen(navController: NavController) {
    val viewModel: ArticleViewModel = hiltViewModel()
    val list = viewModel.newsList.observeAsState()

    Box(modifier = Modifier.fillMaxHeight()){
        list.value?.let { result ->
            when (result) {
                is NetworkResults.Error -> {
                }

                is NetworkResults.Loading -> {
                }
                is NetworkResults.Success -> {
                    ArticlesPager(result.data!!)
                }
            }
        }
        MainMenu(navController = navController)
    }
}