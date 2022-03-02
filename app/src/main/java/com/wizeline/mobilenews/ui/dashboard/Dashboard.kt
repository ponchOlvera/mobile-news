package com.wizeline.mobilenews.ui.dashboard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.ui.theme.CompletelyLight

@Composable
fun DashboardHomeContent(navigateToDetail: (Article) -> Unit) {
    val viewModel: ArticleViewModel = hiltViewModel()
    val list = viewModel.newsList.observeAsState()
    Column {
        ConstraintLayout(modifier = Modifier.fillMaxSize()) {
            val (edit_text, news) = createRefs()
            OutlinedTextField(
                value = viewModel.queryForSearch,
                onValueChange = { query -> viewModel.queryForSearch = query },
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
            list.value?.let { result ->
                when (result) {
                    is NetworkResults.Error -> {
                    }

                    is NetworkResults.Loading -> {
                    }
                    is NetworkResults.Success -> {
                        LazyColumn(
                            contentPadding = PaddingValues(
                                horizontal = 16.dp,
                                vertical = 8.dp
                            ),
                            modifier = Modifier.constrainAs(news) {
                                top.linkTo(edit_text.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(parent.end)
                            }
                        ) {
                            items(
                                items = result.data!!,
                                itemContent = {
                                    ArticleListItem(article = it, navigateToDetail)
                                }
                            )
                        }

                    }
                }
            }
        }
    }
}