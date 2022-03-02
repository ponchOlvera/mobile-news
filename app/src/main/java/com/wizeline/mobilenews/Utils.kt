package com.wizeline.mobilenews

import androidx.annotation.DimenRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
@ReadOnlyComposable
fun fontDimensionResource(@DimenRes id: Int) = dimensionResource(id = id).value.sp

//@Composable
//@ReadOnlyComposable
//fun navigateTo(navController: NavController, screen: String){
//    val currentRoute = navController.currentBackStackEntry?.destination?.route
////    val screenStr = stringResource(screen)
//    if ( currentRoute != screen ){
//        navController.navigate(screen)
//    }
//}