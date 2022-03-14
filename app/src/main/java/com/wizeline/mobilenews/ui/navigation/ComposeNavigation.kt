package com.wizeline.mobilenews.ui.navigation

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.google.gson.Gson
import com.wizeline.mobilenews.R
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.ui.createArticle.CreateArticleScreen
import com.wizeline.mobilenews.ui.custom.CustomPager
import com.wizeline.mobilenews.ui.dashboard.ArticleViewModel
import com.wizeline.mobilenews.ui.dashboard.SearchScreen
import com.wizeline.mobilenews.ui.dashboard.SearchResults
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
    val viewModelStoreOwner = checkNotNull(LocalViewModelStoreOwner.current) {
        "No ViewModelStoreOwner was provided via LocalViewModelStoreOwner"
    }
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
//        composable(
//            "$searchResultsScreen/{articleItem}",
//            arguments = listOf(navArgument("articleItem") {
//                type = NavType.StringType
////                type = NavType.SerializableType(Article::class.java)
////                type = ArticleParamType()
//            })
//        ) { backStackEntry ->
//            backStackEntry.arguments?.getString("articleItem")?.let { jsonString ->
//                val article = jsonString.fromJson(Article::class.java)
//                SearchResults(article)
//            }
////                SearchResults(articles = null)
////            SearchResults(
//////                backStackEntry.arguments?.getString("articleItem")
//////                backStackEntry.arguments?.getSerializable("articleItem") as Article
////                backStackEntry.arguments?.getParcelable<Article>("articleItem")
////            )
//        }
    }
}

fun <A> String.fromJson(type: Class<A>): A {
    return Gson().fromJson(this, type)
}

fun <A> A.toJson(): String? {
    return Gson().toJson(this)
}

class ArticleParamType: NavType<Article>(isNullableAllowed = false){
    override fun get(bundle: Bundle, key: String): Article? {
        return bundle.getParcelable(key)
    }

    override fun parseValue(value: String): Article {
        return Gson().fromJson(value, Article::class.java)
    }

    override fun put(bundle: Bundle, key: String, value: Article) {
        bundle.putParcelable(key, value)
    }
}