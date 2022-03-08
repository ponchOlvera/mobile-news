package com.wizeline.mobilenews.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.asFlow
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.wizeline.mobilenews.ui.theme.CompletelyLight

@Composable
fun DashboardGlobalNews(navController: NavController) {
    val viewModel: ArticleViewModel = hiltViewModel()
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
                    viewModel.getArticlesBySearch(query.text)
                },
                shape = RoundedCornerShape(30.dp),
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = CompletelyLight
                ),
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
                        list[index]?.let { ArticleListItem(article = it) }
                    }
                    list.apply {
                        when {
                            loadState.refresh is LoadState.Loading -> {
                                item { LoadingItem() }
                                item { LoadingItem() }
                            }
                            loadState.append is LoadState.Loading -> {
                                item { LoadingItem() }
                                item { LoadingItem() }
                            }
                            loadState.refresh is LoadState.Error -> {}
                            loadState.append is LoadState.Error -> {}

                        }
                    }
                })
        }
    }
}