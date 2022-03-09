package com.wizeline.mobilenews.ui.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class TabData(
    val tabName: String,
    val isSelected: MutableState<Boolean> = mutableStateOf(false)
)