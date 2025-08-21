package core.designsystem.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import core.designsystem.theme.Primary
import core.designsystem.theme.TextBoxDis
import core.designsystem.theme.TextColor
import core.designsystem.theme.TextWColor
import core.designsystem.theme.mTitle

@Composable
fun MainOutlineButton(modifier: Modifier = Modifier, text: String, enabled: Boolean = true, onClick: () -> Unit) {
    TextButton(
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = Color.Transparent,
            contentColor = TextColor,
            disabledContainerColor = TextBoxDis,
            disabledContentColor = TextWColor
        ),
        border = BorderStroke(width = 1.dp, color = Primary),
        onClick = { onClick() }) {
        Text(text = text, style = mTitle)
    }
    /*
        OutlinedButton(
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = TextWColor,
            contentColor = Primary
        ),
        border = BorderStroke(width = 1.dp, color = Primary),
        onClick = { onClick() }) {
        Text(text = text, style = mTitle)
    }
     */
}

