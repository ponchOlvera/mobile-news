package com.wizeline.mobilenews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wizeline.mobilenews.ui.dashboard.DashboardHomeContent
import com.wizeline.mobilenews.ui.dashboard.DummyArticle
import com.wizeline.mobilenews.ui.theme.MobileNewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileNewsTheme {
                Surface(color = MaterialTheme.colors.background) {
                    mobileNewsApp {
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun mobileNewsApp(navigateToDetail: (DummyArticle) -> Unit) {
    Scaffold(
        content = {
            DashboardHomeContent(navigateToDetail = navigateToDetail)
        }
    )
}