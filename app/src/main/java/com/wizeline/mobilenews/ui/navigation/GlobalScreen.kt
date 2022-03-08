package com.wizeline.mobilenews.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import androidx.navigation.NavController
import com.wizeline.mobilenews.ui.custom.CustomArticlesPager

@Composable
fun GlobalScreen(navController: NavController) {
    val viewModel: GlobalScreenViewModel = hiltViewModel()
    val list = viewModel.getArticles().asFlow()
    Column(modifier = Modifier.fillMaxHeight()) {
        MainMenu(navController = navController)
        CustomArticlesPager(list)
    }
}