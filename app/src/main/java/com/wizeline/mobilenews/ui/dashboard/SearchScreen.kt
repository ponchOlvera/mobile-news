package com.wizeline.mobilenews.ui.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.toUpperCase
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.asFlow
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.wizeline.mobilenews.EMPTY_STRING
import com.wizeline.mobilenews.R
import com.wizeline.mobilenews.ui.custom.LoadingProgressBar
import com.wizeline.mobilenews.ui.custom.ShowErrorOrDialog
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel) {
    val searchResultsScreen = stringResource(R.string.search_results_screen)
    val list =
        viewModel.getArticlesBySearch(viewModel.searchStr).asFlow().collectAsLazyPagingItems()

    Column {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (icon_back, title,edit_text, news) = createRefs()
                IconButton(
                    modifier = Modifier
                        .constrainAs(icon_back) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        },
                    onClick = { navController.popBackStack()}
                ) {
                    Icon(
                        painterResource(R.drawable.ic_baseline_arrow_back_24),
                        contentDescription = EMPTY_STRING,
                    )
                }

            Text(
                text = stringResource(R.string.search).uppercase(),
                modifier = Modifier.constrainAs(title){
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(icon_back.top)
                    bottom.linkTo(icon_back.bottom)
                }
            )

            OutlinedTextField(
                value = viewModel.searchStr,
                onValueChange = { viewModel.updateSearchStr(it) },
                singleLine = true,
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = dimensionResource(R.dimen.default_padding))
                    .constrainAs(edit_text) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(icon_back.bottom)
                    },

                )
            LazyColumn(contentPadding = PaddingValues(
                horizontal = dimensionResource(R.dimen.default_padding),
                vertical = dimensionResource(R.dimen.padding_small)
            ),
                modifier = Modifier.constrainAs(news) {
                    top.linkTo(edit_text.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                content = {
                    items(list.itemCount) { index ->
                        list[index]?.let { ArticleItem(article = it, Modifier.clickable {
                            viewModel.updateLazyArticles(list)
                            viewModel.updateClickedArticle(index)
                            navController.navigate(searchResultsScreen)
                        }) }
                    }
                    list.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                item { LoadingProgressBar() }
                            }
                            loadState.append is LoadState.Loading -> {
                                item { LoadingProgressBar() }
                            }
                            loadState.refresh is LoadState.Error -> {
                                item { ShowErrorOrDialog(loadState.refresh as LoadState.Error) }
                            }
                            loadState.append is LoadState.Error -> {
                                item { ShowErrorOrDialog(loadState.append as LoadState.Error) }
                            }
                        }
                    }
                })
        }
    }
}
