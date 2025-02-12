package com.busschedule.setting.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.busschedule.setting.component.RoundTextField
import com.busschedule.util.state.ApplicationState
import core.designsystem.component.HeightSpacer
import core.designsystem.component.appbar.BackArrowAppBar
import core.designsystem.component.button.MainButton
import core.designsystem.theme.Primary
import core.designsystem.theme.TextMColor
import core.designsystem.theme.TextWColor
import core.designsystem.theme.rTextBox

@Composable
fun AskScreen(appState: ApplicationState) {
    val focusManager = LocalFocusManager.current
    var input by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()

    ) {
        BackArrowAppBar(title = "개발자에게 문의하기") { appState.popBackStack() }
        Column(modifier = Modifier.weight(1f).padding(horizontal = 16.dp)) {
            HeightSpacer(height = 50.dp)
            RoundTextField(value = "", onValueChange = {}, placeholder = "닉네임") {
                focusManager.moveFocus(FocusDirection.Down)
            }
            HeightSpacer(height = 32.dp)
            RoundTextField(value = "", onValueChange = {}, placeholder = "닉네임") {
                focusManager.moveFocus(FocusDirection.Down)
            }
            HeightSpacer(height = 32.dp)
            TextField(
                value = input,
                onValueChange = { input = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(308.dp),
                placeholder = { Text(text = "문의 내용", style = rTextBox.copy(TextMColor)) },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = TextWColor,
                    unfocusedContainerColor = TextWColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Primary,
                ),
                textStyle = rTextBox.copy(TextMColor),
            )
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            MainButton(text = "완료") { appState.popBackStack() }
        }
    }
}