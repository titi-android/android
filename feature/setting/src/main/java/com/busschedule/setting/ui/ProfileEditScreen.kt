package com.busschedule.setting.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import com.busschedule.setting.component.ProfileCard
import com.busschedule.setting.component.RoundTextField
import com.busschedule.util.state.ApplicationState
import core.designsystem.component.HeightSpacer
import core.designsystem.component.appbar.BackArrowAppBar
import core.designsystem.component.button.MainButton

@Composable
fun ProfileEditScreen(appState: ApplicationState) {
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        BackArrowAppBar(title = "설정") { appState.popBackStack() }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HeightSpacer(height = 16.dp)
            ProfileCard(image = "", text = "닉네임")
            HeightSpacer(height = 40.dp)
            RoundTextField(
                value = "",
                onValueChange = {},
                placeholder = "닉네임 변경"
            ) { focusManager.clearFocus() }
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