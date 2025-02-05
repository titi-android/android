package com.busschedule.register.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@Composable
fun SearchTextField(value: String, onValueChange: (String) -> Unit, placeholder: String, onSearch: (String) -> Unit) {
    val focusManager = LocalFocusManager.current
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedPlaceholderColor = Color(0xFF8991B3),
            unfocusedPlaceholderColor = Color(0xFF8991B3),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        maxLines = 1,
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        placeholder = { Text(text = placeholder) },
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions {
            onSearch(value)
            focusManager.clearFocus()
        }
    )
}