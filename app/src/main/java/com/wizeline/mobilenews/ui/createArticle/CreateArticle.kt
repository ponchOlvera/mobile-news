package com.wizeline.mobilenews.ui.createArticle

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.wizeline.mobilenews.DEFAULT_WEIGHT
import com.wizeline.mobilenews.EMPTY_STRING
import com.wizeline.mobilenews.R
import com.wizeline.mobilenews.ui.common.CustomSnackbar
import com.wizeline.mobilenews.ui.common.GradientButton
import com.wizeline.mobilenews.ui.common.OutlinedFormInput
import com.wizeline.mobilenews.ui.custom.LoadingProgressBar
import com.wizeline.mobilenews.ui.theme.MobileNewsTheme
import com.wizeline.mobilenews.ui.theme.SpaceCadet
import com.wizeline.mobilenews.utils.ResourceProvider
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

    val uiState =
        createArticleViewModel.uiState

    var snackbarMessage by remember {
        mutableStateOf(EMPTY_STRING)
    }

    val articleTitle =
        createArticleViewModel.articleTitle
    val articleAuthor =
        createArticleViewModel.articleAuthor

    val articleDescription =
        createArticleViewModel.articleDescription

    val context = LocalContext.current
    val bitmap = remember {
        mutableStateOf(
            ContextCompat.getDrawable(context, R.drawable.select_image)?.toBitmap()
        )
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri: Uri? ->
            createArticleViewModel.updateImageUri(uri ?: Uri.EMPTY)
            uri?.let {
                bitmap.value = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    ImageDecoder.decodeBitmap(source)
                } else {
                    MediaStore.Images.Media.getBitmap(context.contentResolver, it)
                }
            }
        })

    var showLoader by remember {
        mutableStateOf(false)
    }

    val focusKeyboard = LocalSoftwareKeyboardController.current

    val modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(R.dimen.default_padding))

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
    uiState.let {
        when (it) {
            is CreateArticleViewModel.UiState.MissingFieldAction -> {
                snackbarMessage = it.message
                createArticleViewModel.restartUiState()
            }
            is CreateArticleViewModel.UiState.LoadingState -> {
                snackbarMessage = stringResource(R.string.msg_uploading_post)
                showLoader = true
                createArticleViewModel.restartUiState()
            }
            is CreateArticleViewModel.UiState.RequestCompleted -> {
                showLoader = false
                snackbarMessage = it.message
                navController.popBackStack()
                createArticleViewModel.restartUiState()
            }
            else -> {
                snackbarMessage = EMPTY_STRING
            }
        }
    }

    CustomSnackbar(text = snackbarMessage)
    Surface(color = MaterialTheme.colors.background) {
        Column {
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
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
                Text(text = stringResource(R.string.title_create_article), modifier = Modifier
                    .padding(dimensionResource(R.dimen.default_padding))
                    .constrainAs(titleText) {
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        top.linkTo(parent.top)
                    })
            }
            if (showLoader) {
                LoadingProgressBar()
            }
            OutlinedFormInput(
                articleTitle,
                stringResource(R.string.input_placeholder_post_title),
                { createArticleViewModel.updateArticleTitle(it) },
                backgroundInputColor,
                onDoneCallback,
                modifier
            )
            OutlinedFormInput(
                articleAuthor,
                stringResource(R.string.input_placeholder_post_author),
                { createArticleViewModel.updateArticleAuthor(it) },
                backgroundInputColor,
                onDoneCallback,
                modifier
            )
            OutlinedFormInput(
                articleDescription,
                stringResource(R.string.input_placeholder_post_description),
                { createArticleViewModel.updateArticleDescription(it) },
                backgroundInputColor,
                onDoneCallback,
                modifier.height(dimensionResource(R.dimen.post_description_height)),
                false
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(vertical = dimensionResource(R.dimen.padding_small))
            ) {
                Image(
                    bitmap = bitmap.value!!.asImageBitmap(),
                    modifier = Modifier
                        .weight(DEFAULT_WEIGHT)
                        .padding(start = dimensionResource(R.dimen.default_padding))
                        .clip(RoundedCornerShape(dimensionResource(R.dimen.corner_radius)))
                        .height(dimensionResource(R.dimen.post_image_selection_height)),
                    contentScale = ContentScale.Crop,
                    contentDescription = stringResource(R.string.msg_select_image)
                )
                GradientButton(
                    stringResource(R.string.btn_upload_image),
                    modifier = Modifier
                        .padding(
                            start = dimensionResource(R.dimen.padding_small),
                            end = dimensionResource(R.dimen.default_padding)
                        )
                        .weight(DEFAULT_WEIGHT),
                    onClick = {
                        launcher.launch(ResourceProvider.getString(R.string.img_selector_format))
                    })
            }
            Row(Modifier.padding(top = dimensionResource(R.dimen.create_post_btn_top_padding))) {
                GradientButton(
                    text = stringResource(R.string.btn_label_create_post),
                    enabled = !showLoader,
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(R.dimen.default_padding))
                        .weight(DEFAULT_WEIGHT)
                ) {
                    coroutineScope.launch {
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

