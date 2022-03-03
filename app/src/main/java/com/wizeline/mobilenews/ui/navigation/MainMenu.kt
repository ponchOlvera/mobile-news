package com.wizeline.mobilenews.ui.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.wizeline.mobilenews.EMPTY_STR
import com.wizeline.mobilenews.R
import com.wizeline.mobilenews.fontDimensionResource


@Composable
fun MainMenu(navController: NavController){
    val searchScreen = stringResource(R.string.search_screen)
    val communityScreen = stringResource(R.string.community_screen)
    val globalScreen = stringResource(R.string.global_screen)

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
            modifier = Modifier
                .constrainAs(community_lbl) {
                    start.linkTo(parent.start)
                    end.linkTo(global_lbl.start)
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                }
                .padding(dimensionResource(R.dimen.default_padding))
                .clickable(onClick = {
                    navController.navigate(communityScreen){
                        launchSingleTop = true
                    }
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
                .clickable {
                    navController.navigate(globalScreen){
                        launchSingleTop = true
                    }
                },
        )
        IconButton(
            onClick = {
                navController.navigate(searchScreen){
                    launchSingleTop = true
                }
                      },
            modifier = Modifier
                .constrainAs(search_img) {
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