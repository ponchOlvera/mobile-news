package com.wizeline.mobilenews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.wizeline.mobilenews.R
import com.wizeline.mobilenews.ui.createArticle.CreateArticleScreen
import com.wizeline.mobilenews.ui.custom.CustomPager
import com.wizeline.mobilenews.ui.dashboard.ArticleViewModel
import com.wizeline.mobilenews.ui.dashboard.SearchResults
import com.wizeline.mobilenews.ui.dashboard.SearchScreen
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun ComposeNavigation() {
    val navController = rememberNavController()
    val communityScreen = stringResource(R.string.community_screen)
    val globalScreen = stringResource(R.string.global_screen)
    val searchScreen = stringResource(R.string.search_screen)
    val createArticleScreen = stringResource(R.string.create_article_screen)
    val searchResultsScreen = stringResource(R.string.search_results_screen)

    NavHost(navController = navController, startDestination = globalScreen) {
        composable(globalScreen) {
            CustomPager(
                listOf(TabData(communityScreen), TabData(globalScreen)),
                navController = navController
            )
        }
        composable(createArticleScreen) { CreateArticleScreen(navController) }
        navigation(startDestination = searchScreen, route = "searchRoute") {
            composable(searchScreen) {
                val searchBackStackEntry = remember { navController.getBackStackEntry("searchRoute") }
                val viewModel: ArticleViewModel = hiltViewModel(searchBackStackEntry)
                SearchScreen(navController, viewModel)
            }
            composable(searchResultsScreen) {
                val searchBackStackEntry = remember { navController.getBackStackEntry("searchRoute") }
                val viewModel: ArticleViewModel = hiltViewModel(searchBackStackEntry)
                SearchResults(viewModel)
            }
        }
    }
}