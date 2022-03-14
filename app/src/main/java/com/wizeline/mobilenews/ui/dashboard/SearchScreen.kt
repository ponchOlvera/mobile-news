package com.wizeline.mobilenews.ui.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
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
    val textState = remember { mutableStateOf(TextFieldValue()) }
    val list =
        viewModel.getArticlesBySearch(textState.value.text).asFlow().collectAsLazyPagingItems()
    Column {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (edit_text, news) = createRefs()
            OutlinedTextField(
                value = textState.value,
                onValueChange = { query ->
                    textState.value = query
                },
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .constrainAs(edit_text) {
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                    }

            )
            LazyColumn(contentPadding = PaddingValues(
                horizontal = 16.dp,
                vertical = 8.dp
            ),
                modifier = Modifier.constrainAs(news) {
                    top.linkTo(edit_text.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
                content = {
                    items(list.itemCount) { index ->
                        list[index]?.let { ArticleItem(article = it, Modifier.clickable {
                            viewModel.lazyArticles = list
                            viewModel.articleClickedPos = index
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