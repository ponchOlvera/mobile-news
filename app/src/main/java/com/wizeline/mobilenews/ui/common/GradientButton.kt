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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
        shape = RoundedCornerShape(30.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .background(gradient, RoundedCornerShape(30.dp))
                .padding(horizontal = 16.dp, vertical = 8.dp),
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
            text = "Click me!",
            modifier = Modifier.weight(1f)
        ) {

        }
    }
}