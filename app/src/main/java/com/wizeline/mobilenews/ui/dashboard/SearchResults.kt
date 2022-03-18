package com.wizeline.mobilenews.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.wizeline.mobilenews.EMPTY_STRING
import com.wizeline.mobilenews.R
import com.wizeline.mobilenews.ui.custom.SliderArticles
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun SearchResults(
    navController: NavController,
    viewModel: SearchViewModel
) {
    val articles = viewModel.lazyArticles

    SliderArticles(articles, viewModel.articleClickedPos)
    ConstraintLayout(
        Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.menu_height))
            .background(MaterialTheme.colors.background)) {
        val iconBack = createRef()
        IconButton(
            modifier = Modifier
                .constrainAs(iconBack) {
                    start.linkTo(parent.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                },
            onClick = { navController.popBackStack()}
        ) {
            Icon(
                painterResource(R.drawable.ic_baseline_arrow_back_24),
                contentDescription = EMPTY_STRING,
            )
        }
    }

}