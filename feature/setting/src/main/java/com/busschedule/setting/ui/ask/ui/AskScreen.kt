package com.busschedule.setting.ui.ask.ui

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.busschedule.setting.component.RoundTextField
import com.busschedule.setting.ui.ask.AskViewModel
import com.busschedule.setting.ui.ask.entity.AskUIState
import com.busschedule.util.state.ApplicationState
import core.designsystem.component.HeightSpacer
import core.designsystem.component.appbar.BackArrowAppBar
import core.designsystem.component.button.MainBottomButton
import core.designsystem.theme.Primary
import core.designsystem.theme.TextMColor
import core.designsystem.theme.TextWColor
import core.designsystem.theme.rTextBox

@Composable
fun AskScreen(appState: ApplicationState, viewModel: AskViewModel = hiltViewModel()) {
    val focusManager = LocalFocusManager.current

    val uiState by viewModel.askUIState.collectAsStateWithLifecycle(AskUIState())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()

    ) {
        BackArrowAppBar(title = "개발자에게 문의하기") { appState.popBackStack() }
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp)
        ) {
            HeightSpacer(height = 16.dp)
            RoundTextField(
                value = uiState.title,
                onValueChange = { viewModel.updateTitle(it) },
                placeholder = "제목"
            ) {
                focusManager.moveFocus(FocusDirection.Down)
            }
            HeightSpacer(height = 32.dp)
            TextField(
                value = uiState.content,
                onValueChange = { viewModel.updateContent(it) },
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
        MainBottomButton(text = "보내기") {
            viewModel.fetchPostInquiry(showToast = appState::showToastMsg) {
                appState.navigateToSetting()
            }
        }
    }
}