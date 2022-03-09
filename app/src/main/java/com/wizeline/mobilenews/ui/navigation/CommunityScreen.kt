package com.wizeline.mobilenews.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.wizeline.mobilenews.EMPTY_STR
import com.wizeline.mobilenews.R
import com.wizeline.mobilenews.ui.theme.Percent50Light

@Composable
fun CommunityScreen(navController: NavController) {
    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (fabBtn) = createRefs()
        FloatingActionButton(
            onClick = { /*TODO*/ },
            backgroundColor = Percent50Light,
            modifier = Modifier
                .padding(8.dp)
                .constrainAs(fabBtn) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }
        )
        {
            Icon(
                painterResource(R.drawable.ic_baseline_add_24),
                contentDescription = EMPTY_STR,
                tint = Color.White,
            )
        }
    }

}