package com.wizeline.mobilenews.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wizeline.mobilenews.EMPTY_STRING

@Composable
fun OutlinedFormInput(
    inputValue: String = EMPTY_STRING,
    placeholder: String = EMPTY_STRING,
    onValueChange: (String) -> Unit,
    backgroundInputColor: Color,
    onDoneCallback: () -> Unit,
    modifier: Modifier,
    singleLine: Boolean = true
) {
    OutlinedTextField(
        value = inputValue,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = backgroundInputColor
        ),
        keyboardActions = KeyboardActions(onDone = { onDoneCallback() }),
        placeholder = {
            Text(placeholder)
        },
        singleLine = singleLine,
        onValueChange = { onValueChange(it) },
        shape = RoundedCornerShape(30.dp),
        modifier = modifier
    )
}