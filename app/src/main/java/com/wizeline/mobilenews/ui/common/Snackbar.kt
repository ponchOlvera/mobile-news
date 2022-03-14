package com.wizeline.mobilenews.ui.common

import android.annotation.SuppressLint
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CustomSnackbar(text: String) {
    val scaffoldState = rememberScaffoldState()
    val snackbarCoroutineScope = rememberCoroutineScope()

    Scaffold(scaffoldState = scaffoldState) {
        Button(onClick = {
            snackbarCoroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar(text)
            }
        }) {
            Text(text)
        }
    }

    snackbarCoroutineScope.launch {
        if (text.isNotEmpty())
            scaffoldState.snackbarHostState.showSnackbar(text)
    }
}