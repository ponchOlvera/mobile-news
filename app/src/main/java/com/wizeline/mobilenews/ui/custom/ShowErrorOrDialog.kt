package com.wizeline.mobilenews.ui.custom

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import com.wizeline.mobilenews.R
import com.wizeline.mobilenews.ui.theme.Typography

@Composable
fun ShowErrorOrDialog(state: LoadState.Error) {
    if (state.error.message.toString() == stringResource(R.string.empty_list)) {
        Text(
            text = stringResource(id = R.string.empty_list_message),
            color = Color.LightGray,
            textAlign = TextAlign.Center,
            style = Typography.body1,
            modifier = Modifier
                .padding(dimensionResource(R.dimen.padding_small))
        )
    } else {
        CustomDialog(stringResource(R.string.error_message))
    }
}