package com.wizeline.mobilenews.ui.dashboard

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.wizeline.mobilenews.EMPTY_STRING
import com.wizeline.mobilenews.R
import com.wizeline.mobilenews.ui.custom.SliderArticles
import com.wizeline.mobilenews.ui.dashboard.CommunityViewModel
import com.wizeline.mobilenews.ui.theme.Percent10Dark
import com.wizeline.mobilenews.ui.theme.Percent50Light
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
@Composable
fun CommunityScreen(navController: NavController) {
    val isDarkTheme = !MaterialTheme.colors.isLight
    val fabBackgroundColor = if (isDarkTheme) Percent50Light else Percent10Dark
    val viewModel: CommunityViewModel = hiltViewModel()
    val articles = viewModel.getArticles().asFlow()
    val lazyArticles = articles.collectAsLazyPagingItems()
    val createArticleScreen = stringResource(R.string.create_article_screen)
    viewModel.getArticles()
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (fabBtn) = createRefs()
        SliderArticles(lazyArticles)
        FloatingActionButton(
            onClick = {
                navController.navigate(createArticleScreen) {
                    launchSingleTop = true
                }
            },
            backgroundColor = fabBackgroundColor,
            elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp),
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small))
                .constrainAs(fabBtn) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        {
            Icon(
                painterResource(R.drawable.ic_baseline_add_24),
                contentDescription = EMPTY_STRING,
            )
        }
    }

}