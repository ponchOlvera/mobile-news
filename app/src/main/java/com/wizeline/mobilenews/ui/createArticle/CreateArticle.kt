package com.wizeline.mobilenews.ui.createArticle

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.wizeline.mobilenews.EMPTY_STRING
import com.wizeline.mobilenews.R
import com.wizeline.mobilenews.ui.common.GradientButton
import com.wizeline.mobilenews.ui.common.OutlinedFormInput
import com.wizeline.mobilenews.ui.theme.MobileNewsTheme
import com.wizeline.mobilenews.ui.theme.SpaceCadet
import kotlinx.coroutines.launch

@Composable
fun CreateArticleScreen(navController: NavController) {

    MobileNewsTheme {
        ConstraintLayout(modifier = Modifier.fillMaxHeight()) {
            ArticleForm(navController = navController)
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun ArticleForm(navController: NavController) {

    val createArticleViewModel: CreateArticleViewModel = hiltViewModel()
    val coroutineScope = rememberCoroutineScope()

    val uiState = remember {
        createArticleViewModel.uiState
    }

    var articleName by remember {
        createArticleViewModel.articleName
    }
    var articleAuthor by remember {
        createArticleViewModel.articleAuthor
    }
    var articleDescription by remember {
        createArticleViewModel.articleDescription
    }
    var imageUri by remember {
        createArticleViewModel.imageUri
    }
    val context = LocalContext.current
    var bitmap by remember {
        mutableStateOf(
            ContextCompat.getDrawable(context, R.drawable.select_image)?.toBitmap()
        )
    }
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            imageUri = uri
            imageUri?.let {
                bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    ImageDecoder.decodeBitmap(source)
                } else {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                }
            }
        })

    val focusKeyboard = LocalSoftwareKeyboardController.current

    val modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)

    val onDoneCallback: () -> Unit = {
        focusKeyboard?.hide()
    }

    // Define colors according to UI mode
    val backgroundInputColor: Color
    if (MaterialTheme.colors.isLight) {
        backgroundInputColor = MaterialTheme.colors.background
    } else {
        backgroundInputColor = SpaceCadet
    }

    //Handle the UiState
    uiState.value.let {
        when (it) {
            is CreateArticleViewModel.UiState.MissingFieldAction -> Toast.makeText(
                context,
                it.message,
                Toast.LENGTH_SHORT
            ).show()
            is CreateArticleViewModel.UiState.LoadingState -> Toast.makeText(
                context,
                "Uploading post...",
                Toast.LENGTH_SHORT
            ).show()
            is CreateArticleViewModel.UiState.RequestCompleted -> {
                Log.i("CreateArticle()", it.message)
                Toast.makeText(
                    context,
                    it.message,
                    Toast.LENGTH_SHORT
                ).show()
                navController.popBackStack()
                uiState.value = CreateArticleViewModel.UiState.NoState
            }
            else -> {}
        }
    }
    Surface(color = MaterialTheme.colors.background) {
        Column {
            ConstraintLayout(modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()) {
                val (backIcon, titleText) = createRefs()
                IconButton(
                    onClick = {
                        navController.popBackStack()
                    },
                    modifier = Modifier
                        .constrainAs(backIcon) {
                            start.linkTo(parent.start)
                            top.linkTo(parent.top)
                        },
                ) {
                    Icon(
                        painterResource(R.drawable.ic_baseline_arrow_back_24),
                        contentDescription = EMPTY_STRING,
                        tint = Color.White,
                    )
                }
                Text(text = "CREATE ARTICLE", modifier = Modifier
                    .padding(dimensionResource(R.dimen.default_padding))
                    .constrainAs(titleText) {
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    })
            }
            OutlinedFormInput(
                articleName,
                "Post title",
                { articleName = it },
                backgroundInputColor,
                onDoneCallback,
                modifier
            )
            OutlinedFormInput(
                articleAuthor,
                "Post author",
                { articleAuthor = it },
                backgroundInputColor,
                onDoneCallback,
                modifier
            )
            OutlinedFormInput(
                articleDescription,
                "Post description",
                { articleDescription = it },
                backgroundInputColor,
                onDoneCallback,
                modifier.height(180.dp),
                false
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                Image(
                    bitmap = bitmap!!.asImageBitmap(),
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 16.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .height(120.dp),
                    contentScale = ContentScale.Crop,
                    contentDescription = "Select an image..."
                )
                GradientButton("UPLOAD IMAGE",
                    modifier = Modifier
                        .padding(start = 8.dp, end = 16.dp)
                        .weight(1f),
                    onClick = {
                        launcher.launch("image/*")
                    })
            }
            Row(Modifier.padding(top = 24.dp)) {
                GradientButton(
                    text = "CREATE POST",
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .weight(1f)
                ) {
                    coroutineScope.launch() {
                        createArticleViewModel.createCommunityPost()
                    }
                }
            }
        }
    }
}


@Preview(
    name = "Form preview",
    showSystemUi = true,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_NO
)
@Composable
private fun CreateArticleScreenPreview() {
    MobileNewsTheme {
        ArticleForm(navController = rememberNavController())
    }
}

