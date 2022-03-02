package com.wizeline.mobilenews.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import com.wizeline.mobilenews.EMPTY_STR
import com.wizeline.mobilenews.R
import com.wizeline.mobilenews.data.models.NetworkResults
import com.wizeline.mobilenews.fontDimensionResource
import com.wizeline.mobilenews.ui.dashboard.ArticleViewModel
import com.wizeline.mobilenews.ui.theme.Percent50Light

@Composable
fun PagerHomeContent() {
    val viewModel: ArticleViewModel = hiltViewModel()
    val list = viewModel.newsList.observeAsState()

    Box(modifier = Modifier.fillMaxHeight()){
        list.value?.let { result ->
            when (result) {
                is NetworkResults.Error -> {
                }

                is NetworkResults.Loading -> {
                }
                is NetworkResults.Success -> {
                    ArticlesPager(result.data!!)
                }
            }
        }
//        OutlinedTextField(
//            value = viewModel.queryForSearch,
//            onValueChange = { query -> viewModel.queryForSearch = viewModel.queryForSearch + query },
//            shape = RoundedCornerShape(30.dp),
//            colors = TextFieldDefaults.textFieldColors(
//                backgroundColor = Percent50Light
//            ),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        )
        Row(modifier = Modifier
            .fillMaxWidth()
        ) {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val (community_lbl, global_lbl, search_img) = createRefs()
                createHorizontalChain(community_lbl, global_lbl, chainStyle = ChainStyle.Packed)
                Text(
                    text = stringResource(R.string.community_title),
                    color = Color.White,
                    fontSize = fontDimensionResource(R.dimen.subtitle_size),
                    textAlign = TextAlign.Right,
                    modifier = Modifier
                        .constrainAs(community_lbl) {
                            start.linkTo(parent.start)
                            end.linkTo(global_lbl.start)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .padding(dimensionResource(R.dimen.default_padding))
                        .clickable(onClick = {
                            print("OnClick community")
                        })
                )
                Text(
                    text = stringResource(R.string.global_title),
                    color = Color.White,
                    fontSize = fontDimensionResource(R.dimen.subtitle_size),
                    modifier = Modifier
                        .constrainAs(global_lbl) {
                            end.linkTo(parent.end)
                            start.linkTo(community_lbl.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                        }
                        .padding(dimensionResource(R.dimen.default_padding))
                        .clickable { print("OnClick global") },
                    textAlign = TextAlign.Start,
                )
                IconButton(
                    onClick = {print("OnClick search")},
                    modifier = Modifier
                        .constrainAs(search_img){
                            end.linkTo(parent.end)
                            top.linkTo(parent.top)
                            bottom.linkTo(parent.bottom)
                    }
                        .padding(dimensionResource(R.dimen.default_padding)),
                    ){
                    Icon(
                        painterResource(R.drawable.ic_baseline_search_24),
                        contentDescription = EMPTY_STR,
                        tint = Color.White,
                    )
                }
            }

        }
//        var text by rememberSaveable { mutableStateOf("") }
//        OutlinedTextField(
//            value = text,
//            onValueChange = { query ->
//                text = query
//                            },
//            singleLine = true,
//            placeholder = { Text(text = stringResource(R.string.search))},
//            shape = RoundedCornerShape(dimensionResource(R.dimen.padding_30)),
//            colors = TextFieldDefaults.textFieldColors(
//                backgroundColor = Percent50Light
//            ),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        )
    }
}