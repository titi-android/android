package core.designsystem.component.button

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.designsystem.theme.Primary
import core.designsystem.theme.TextWColor
import core.designsystem.theme.mTitle

@Composable
fun MainOutlineButton(text: String, enabled: Boolean = true, onClick: () -> Unit) {
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
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
}

