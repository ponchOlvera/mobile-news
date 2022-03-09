package com.wizeline.mobilenews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.ui.navigation.ComposeNavigation
import com.wizeline.mobilenews.ui.theme.MobileNewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContent {
            MobileNewsTheme {
                Surface {
                    mobileNewsApp()
                }
            }
        }
    }
}

@Composable
fun mobileNewsApp() {
    Scaffold(
        content = {
            ComposeNavigation()
        }
    )
}