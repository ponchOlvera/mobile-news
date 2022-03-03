package com.wizeline.mobilenews.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wizeline.mobilenews.R
import com.wizeline.mobilenews.ui.dashboard.DashboardHomeContent

@Composable
fun ComposeNavigation(){
    val navController = rememberNavController()
    val communityScreen = stringResource(R.string.community_screen)
    val globalScreen = stringResource(R.string.global_screen)
    val searchScreen = stringResource(R.string.search_screen)
    NavHost(navController = navController, startDestination = globalScreen) {
        composable(globalScreen) { GlobalScreen(navController) }
        composable(communityScreen) { CommunityScreen(navController) }
        composable(searchScreen) { DashboardHomeContent{} }
    }
}