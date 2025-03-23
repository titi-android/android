package core.designsystem.component.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import core.designsystem.component.HeightSpacer
import core.designsystem.component.WidthSpacer
import core.designsystem.component.button.MainButton
import core.designsystem.component.button.MainOutlineButton
import core.designsystem.theme.TextColor
import core.designsystem.theme.TextWColor
import core.designsystem.theme.sbTitle3

@Composable
fun TitleDialog(title: String, leftBtnText: String, rightBtnText: String, onDismissRequest: () -> Unit, onNotComplete: () -> Unit, onComplete: () -> Unit) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            colors = CardDefaults.cardColors(containerColor = TextWColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(
                    top = 20.dp,
                    bottom = 10.dp,
                    start = 17.dp,
                    end = 17.dp
                ), horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = title, style = sbTitle3.copy(TextColor))
                HeightSpacer(height = 8.dp)
//                Text(text = content, style = rTextLabel.copy(TextMColor))
//                HeightSpacer(height = 20.dp)
                Row(modifier = Modifier.padding(top = 10.dp)) {
                    Box(modifier = Modifier.weight(1f)) {
                        MainOutlineButton(text = leftBtnText) {
                            onNotComplete()
                            onDismissRequest()
                        }
                    }
                    WidthSpacer(width = 10.dp)
                    Box(modifier = Modifier.weight(1f)) {
                        MainButton(text = rightBtnText) {
                            onComplete()
                            onDismissRequest()
                        }
                    }
                }
            }
        }
    }
}