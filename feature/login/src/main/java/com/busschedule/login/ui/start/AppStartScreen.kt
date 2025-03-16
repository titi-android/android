package com.busschedule.login.ui.start

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.busschedule.common.R
import com.busschedule.util.state.ApplicationState
import core.designsystem.component.WidthSpacer
import core.designsystem.component.button.MainBottomButton
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcMainlogo
import core.designsystem.theme.bigLogo
import core.designsystem.theme.mBody2

@Composable
fun AppStartScreen(appState: ApplicationState) {
    Column(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(0.22f))
        Column(
            modifier = Modifier
                .weight(0.54f)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LogoArea()
            BtnArea(onClickOfLogin = {appState.navigateToLogin()}) { appState.navigateToSignUp() }
        }
        Box(modifier = Modifier.weight(0.24f))

    }
}

@Composable
fun LogoArea() {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            imageVector = MyIconPack.IcMainlogo,
            contentDescription = "image_mainlogo",
            modifier = Modifier.size(150.dp),
        )
        Text(text = stringResource(id = R.string.app_name), style = bigLogo)

    }
}

@Composable
fun BtnArea(onClickOfLogin: () -> Unit, onClickOfSignUp: () -> Unit) {
    val findIdPwColor = Color(0xFF666666)
    Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.End) {
        MainBottomButton(text = "로그인") {
            onClickOfLogin()
        }
        MainBottomButton(text = "회원가입") {
            onClickOfSignUp()
        }
        Row(
            modifier = Modifier.height(IntrinsicSize.Min).padding(end = 16.dp),
        ) {
            Text(text = "아이디 찾기", style = mBody2.copy(findIdPwColor))
            WidthSpacer(12.dp)
            VerticalDivider(thickness = 1.dp, color = findIdPwColor)
            WidthSpacer(12.dp)
            Text(text = "비밀번호 찾기", style = mBody2.copy(findIdPwColor))
        }
    }
}