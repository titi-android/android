package core.designsystem.component.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import core.designsystem.theme.ErrorColor
import core.designsystem.theme.Primary
import core.designsystem.theme.TextWColor
import core.designsystem.theme.rTextLabel

@Composable
fun PrimaryOutlineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    errorText: String,
    isError: Boolean = false,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: () -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onValueChange(it) },
//        placeholder = { Text(text = placeholder, style = rTextBox) },
        label = {
            Text(
                text = placeholder, style = rTextLabel
            )
        },
        supportingText = { Text(text = errorText) },
        isError = isError,
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        keyboardActions = KeyboardActions { keyboardActions() },
        colors = OutlinedTextFieldDefaults.colors(
//            focusedContainerColor = TextWColor,
//            unfocusedContainerColor = TextWColor,
            errorContainerColor = TextWColor,
            focusedTextColor = Primary,
            unfocusedTextColor = Primary,
            errorSupportingTextColor = ErrorColor,
            focusedBorderColor = Primary,
            unfocusedBorderColor = Primary,
            errorBorderColor = ErrorColor,
            focusedLabelColor = Primary,
            unfocusedLabelColor = Primary,
            errorLabelColor = ErrorColor,
            focusedPlaceholderColor = Primary,
            unfocusedPlaceholderColor = Primary,
            cursorColor = Primary,
            errorTextColor = ErrorColor,
            focusedSupportingTextColor = Color.Transparent,
            unfocusedSupportingTextColor = Color.Transparent
        ),
    )
}