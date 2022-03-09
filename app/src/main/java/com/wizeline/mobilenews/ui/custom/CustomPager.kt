package com.wizeline.mobilenews.ui.custom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.wizeline.mobilenews.ui.dashboard.GlobalNewsScreen
import com.wizeline.mobilenews.ui.navigation.MainMenu
import com.wizeline.mobilenews.ui.navigation.TabData

@Composable
fun CustomPager(tabs: List<TabData>, navController: NavController) {
    val selectedTab = remember { mutableStateOf(tabs.size - 1) }
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        MainMenu(
            tabsData = tabs,
            navController = navController,
            isSearchButton = true
        ) { index ->
            selectedTab.value = index
        }
        if (selectedTab.value == 0) {
            Box(modifier = Modifier.fillMaxSize().background(Color.Cyan))
        } else {
            GlobalNewsScreen()
        }

    }
}

