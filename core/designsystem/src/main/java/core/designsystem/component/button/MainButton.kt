package core.designsystem.component.button

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.designsystem.theme.Primary
import core.designsystem.theme.TextBoxDis
import core.designsystem.theme.TextWColor
import core.designsystem.theme.mTitle

@Composable
fun MainButton(modifier: Modifier = Modifier, text: String, enabled: Boolean = true, onClick: () -> Unit) {
    TextButton(
        modifier = Modifier.fillMaxWidth(),
        enabled = enabled,
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.textButtonColors(
            containerColor = Primary,
            contentColor = TextWColor,
            disabledContainerColor = TextBoxDis,
            disabledContentColor = TextWColor
        ),
        onClick = { onClick() }) {
        Text(text = text, style = mTitle)
    }
}
@Composable
fun MainBottomButton(modifier: Modifier = Modifier, text: String, enabled: Boolean = true, onClick: () -> Unit) {
    Box(modifier = modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 10.dp)) {
        TextButton(
            modifier = Modifier.fillMaxWidth(),
            enabled = enabled,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.textButtonColors(
                containerColor = Primary,
                contentColor = TextWColor,
                disabledContainerColor = TextBoxDis,
                disabledContentColor = TextWColor
            ),
            onClick = { onClick() }) {
            Text(text = text, style = mTitle)
        }
    }
}