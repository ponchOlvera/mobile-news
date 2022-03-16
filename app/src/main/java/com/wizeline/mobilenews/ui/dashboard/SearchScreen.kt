package com.wizeline.mobilenews.ui.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.lifecycle.asFlow
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
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
            val (edit_text, news) = createRefs()
            OutlinedTextField(
                value = viewModel.searchStr,
                onValueChange = { viewModel.updateSearchStr(it) },
                singleLine = true,
                shape = RoundedCornerShape(dimensionResource(R.dimen.corner_radius)),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(dimensionResource(R.dimen.default_padding))
                    .constrainAs(edit_text) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
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

@Composable
fun ShowErrorOrDialog(state: LoadState.Error) {
    if (state.error.message.toString() == stringResource(R.string.empty_list)) {
        Text(
            text = stringResource(id = R.string.empty_list_message),
            textAlign = TextAlign.Center,
            style = Typography.body1,
            modifier = Modifier
                .padding(8.dp)
        )
    } else {
        CustomDialog(stringResource(R.string.error_message))
    }
}

@Composable
fun LoadingItem() {
    CircularProgressIndicator(
        modifier =
        Modifier
            .testTag("ProgressBarItem")
            .fillMaxWidth()
            .padding(16.dp)
            .wrapContentWidth(
                Alignment.CenterHorizontally
            )
    )
}