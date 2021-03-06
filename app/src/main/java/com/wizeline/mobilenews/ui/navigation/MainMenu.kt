package com.wizeline.mobilenews.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.wizeline.mobilenews.EMPTY_STRING
import com.wizeline.mobilenews.R
import com.wizeline.mobilenews.ui.theme.GradientEnd
import com.wizeline.mobilenews.ui.theme.GradientStart
import com.wizeline.mobilenews.ui.theme.Typography

@Composable
fun MainMenu(
    tabsData: List<TabData>,
    navController: NavController,
    isSearchButton: Boolean = false,
    whenIsClicked: (Int) -> Unit
) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .height(dimensionResource(R.dimen.menu_height)),
    ) {
        val (tabs, searchButton) = createRefs()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(tabs) {
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    top.linkTo(parent.top)
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val selected = remember { tabsData.map { it.isSelected } }
            tabsData.mapIndexed { index, tab ->
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = tab.tabName,
                        style = if (selected[index].value) Typography.h3 else Typography.h2,
                        modifier = Modifier
                            .padding(
                                top = dimensionResource(R.dimen.mini_padding),
                                start = dimensionResource(R.dimen.mini_padding),
                                end = dimensionResource(R.dimen.mini_padding)
                            )
                            .clickable(onClick = {
                                selected.map { it.value = false }
                                selected[index].value = true
                                whenIsClicked.invoke(index)
                            })
                    )
                    if (selected[index].value) {
                        Box(
                            Modifier
                                .size(dimensionResource(R.dimen.bottom_dot_size))
                                .clip(CircleShape)
                                .background(
                                    brush = Brush.linearGradient(
                                        colors = listOf(GradientStart, GradientEnd),
                                    ),
                                )
                        )
                    } else {
                        Box {}
                    }

                }
            }
        }
        if (isSearchButton) {
            val searchScreen = stringResource(R.string.search_screen)
            IconButton(
                onClick = {
                    navController.navigate(searchScreen) {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier
                    .constrainAs(searchButton) {
                        end.linkTo(parent.end)
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    }
                    .padding(dimensionResource(R.dimen.default_padding)),
            ) {
                Icon(
                    painterResource(R.drawable.ic_baseline_search_24),
                    contentDescription = EMPTY_STRING,
                )
            }
        }
    }
}
