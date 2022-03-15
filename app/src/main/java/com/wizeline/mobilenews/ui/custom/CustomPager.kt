package com.wizeline.mobilenews.ui.custom

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.wizeline.mobilenews.FIRST_LIST_ITEM_POS
import com.wizeline.mobilenews.ui.dashboard.GlobalNewsPage
import com.wizeline.mobilenews.ui.navigation.CommunityScreen
import com.wizeline.mobilenews.ui.navigation.MainMenu
import com.wizeline.mobilenews.ui.navigation.TabData

@Composable
fun CustomPager(tabs: List<TabData>, navController: NavController) {
    val selectedTab = remember { mutableStateOf(tabs.size - 1) }
    tabs[tabs.size - 1].isSelected.value = true
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
        if (selectedTab.value == FIRST_LIST_ITEM_POS) {
            CommunityScreen(navController = navController)
        } else {
            GlobalNewsPage()
        }
    }
}

