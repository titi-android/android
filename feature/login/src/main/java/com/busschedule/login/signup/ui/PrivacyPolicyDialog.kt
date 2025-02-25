package com.busschedule.login.signup.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import core.designsystem.component.HeightSpacer
import core.designsystem.component.appbar.BackArrowAppBar
import core.designsystem.component.button.MainBottomButton
import core.designsystem.theme.mBody

@Composable
fun PrivacyPolicyDialog(popBackStack: () -> Unit, onCheck: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        BackArrowAppBar(title = "이용약관 동의") { popBackStack() }
        HeightSpacer(height = 32.dp)
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 16.dp),
        ) {
            Text(
                text = stringResource(id = com.busschedule.common.R.string.privacy_policy),
                style = mBody
            )
        }
        MainBottomButton(text = "동의") { onCheck() }
    }
}