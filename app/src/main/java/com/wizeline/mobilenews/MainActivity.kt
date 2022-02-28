package com.wizeline.mobilenews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.ui.dashboard.DashboardHomeContent
import com.wizeline.mobilenews.ui.theme.MobileNewsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileNewsTheme {
                Surface {
                    mobileNewsApp {
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun mobileNewsApp(navigateToDetail: (Article) -> Unit) {
    Scaffold(
        content = {
            DashboardHomeContent(navigateToDetail = navigateToDetail)
        }
    )
}