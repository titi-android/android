package core.designsystem.component.textfield

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.busschedule.util.ext.noRippleClickable
import core.designsystem.theme.ErrorColor
import core.designsystem.theme.Primary
import core.designsystem.theme.TextWColor
import core.designsystem.theme.rTextLabel

@Composable
fun PasswordOutlineTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    errorText: String,
    isError: Boolean = false,
    imeAction: ImeAction = ImeAction.Done,
    keyboardActions: () -> Unit,
) {
    var isShowPasswd by remember { mutableStateOf(value = true) }
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        onValueChange = { onValueChange(it) },
        label = { Text(text = placeholder, style = rTextLabel) },
        supportingText = { Text(text = errorText) },
        trailingIcon = {
            Icon(
                imageVector = if (isShowPasswd) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                contentDescription = "ic_visibility",
                tint = Primary,
                modifier = Modifier.noRippleClickable { isShowPasswd = !isShowPasswd }
            )
        },
        visualTransformation = if (isShowPasswd) PasswordVisualTransformation() else VisualTransformation.None,
        isError = isError,
        maxLines = 1,
        singleLine = true,
        keyboardOptions = KeyboardOptions(imeAction = imeAction),
        keyboardActions = KeyboardActions { keyboardActions() },
        colors = OutlinedTextFieldDefaults.colors(
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