package com.busschedule.register.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import core.designsystem.theme.TextMColor
import core.designsystem.theme.rTextBox

@Composable
fun ScheduleNameTextField(value: String, onValueChange: (String) -> Unit, placeholder: String) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        textStyle = rTextBox.copy(TextMColor),
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        placeholder = { Text(text = placeholder, color = Color(0xFF808991)) },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions { focusManager.clearFocus() }
    )
}