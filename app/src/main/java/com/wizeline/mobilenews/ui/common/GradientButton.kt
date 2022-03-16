package com.wizeline.mobilenews.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wizeline.mobilenews.DEFAULT_WEIGHT
import com.wizeline.mobilenews.R
import com.wizeline.mobilenews.ui.theme.Capri
import com.wizeline.mobilenews.ui.theme.MediumPurple

@Composable
fun GradientButton(
    text: String,
    modifier: Modifier = Modifier,
    textColor: Color = Color.White,
    gradient: Brush = Brush.horizontalGradient(
        colors = listOf(
            MediumPurple,
            Capri
        )
    ),
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    Button(
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color.Transparent
        ),
        contentPadding = PaddingValues(),
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(dimensionResource(R.dimen.padding_30))
    ) {
        Box(
            modifier = Modifier
                .weight(DEFAULT_WEIGHT)
                .background(gradient, RoundedCornerShape(dimensionResource(R.dimen.padding_30)))
                .padding(dimensionResource(R.dimen.default_padding)),
            contentAlignment = Alignment.Center
        ) {
            Text(text = text, color = textColor)
        }
    }
}

@Preview(showBackground = true, heightDp = 320, widthDp = 320)
@Composable
fun PreviewGradientButton() {
    Row() {
        GradientButton(
            text = stringResource(R.string.btn_click_me),
            modifier = Modifier.weight(DEFAULT_WEIGHT)
        ) {

        }
    }
}