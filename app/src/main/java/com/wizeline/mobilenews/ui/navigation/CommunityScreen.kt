package com.wizeline.mobilenews.ui.navigation

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import com.wizeline.mobilenews.EMPTY_STRING
import com.wizeline.mobilenews.R
import com.wizeline.mobilenews.domain.models.Article
import com.wizeline.mobilenews.ui.theme.Percent50Light


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

val listArticles = listOf(
    article1, article2,article1, article2, article1, article2
)
@Composable
fun CommunityScreen(navController: NavController){

    ConstraintLayout(modifier = Modifier.fillMaxHeight()) {
        val (fabBtn) = createRefs()
        ArticlesPager(articles = listArticles )
        MainMenu(navController = navController)
        FloatingActionButton(
            onClick = { /*TODO*/ },
            backgroundColor = Percent50Light,
            modifier = Modifier.constrainAs(fabBtn){
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }
        )
        {
            Icon(
                painterResource(R.drawable.ic_baseline_add_24),
                contentDescription = EMPTY_STRING,
                tint = Color.White,
            )
        }
    }

}