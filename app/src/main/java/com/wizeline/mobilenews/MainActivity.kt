package com.wizeline.mobilenews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.ui.dashboard.DashboardHomeContent
import com.wizeline.mobilenews.ui.navigation.PagerHomeContent
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

val article1  = Article(
    "Kotlin jetpack extensions are evolving!",
    "Lissandra",
    "22",
    "https://image.shutterstock.com/image-vector/breaking-news-background-world-global-260nw-719766118.jpg",
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent varius vestibulum massa eget accumsan. Pellentesque accumsan pretium lectus congue aliquam. Morbi facilisis nisl neque, quis egestas mauris volutpat at. Sed sollicitudin eros nec iaculis ornare. In lobortis odio sed nisi dictum sagittis. Aenean porta elementum lectus et mollis. Nulla hendrerit egestas urna eu aliquet. Praesent lobortis laoreet tempus. Morbi ultricies, lorem eget vulputate porttitor, metus nulla egestas lorem, eget egestas magna orci ut dui. Vivamus molestie dolor mi, vel ultricies lectus efficitur nec. Cras suscipit sodales maximus. Quisque suscipit tortor ut ligula vestibulum, vel fermentum metus porta. Pellentesque tempor enim nunc, sit amet ultrices tellus dapibus vitae. Mauris ac lectus eget velit fermentum egestas. Integer vitae enim scelerisque mauris malesuada dignissim ut cursus lorem. Donec fermentum rutrum tortor et congue.")

val article2  = Article(
    "Another article",
    "Other",
    "22",
    "https://st.depositphotos.com/1006899/3776/i/950/depositphotos_37765339-stock-photo-news.jpg",
    "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Praesent varius vestibulum massa eget accumsan. Pellentesque accumsan pretium lectus congue aliquam. Morbi facilisis nisl neque, quis egestas mauris volutpat at. Sed sollicitudin eros nec iaculis ornare. In lobortis odio sed nisi dictum sagittis. Aenean porta elementum lectus et mollis. Nulla hendrerit egestas urna eu aliquet. Praesent lobortis laoreet tempus. Morbi ultricies, lorem eget vulputate porttitor, metus nulla egestas lorem, eget egestas magna orci ut dui. Vivamus molestie dolor mi, vel ultricies lectus efficitur nec. Cras suscipit sodales maximus. Quisque suscipit tortor ut ligula vestibulum, vel fermentum metus porta. Pellentesque tempor enim nunc, sit amet ultrices tellus dapibus vitae. Mauris ac lectus eget velit fermentum egestas. Integer vitae enim scelerisque mauris malesuada dignissim ut cursus lorem. Donec fermentum rutrum tortor et congue.")

val listArticles = listOf<Article>(
    article1, article2,article1, article2, article1, article2
)

@Composable
fun mobileNewsApp(navigateToDetail: (Article) -> Unit) {
    Scaffold(
        content = {
            PagerHomeContent()
//            DashboardHomeContent(navigateToDetail = navigateToDetail)
        }
    )
}