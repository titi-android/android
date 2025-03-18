package com.busschedule.login.ui.splash.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.busschedule.login.ui.splash.SplashViewModel
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcMainlogo
import core.designsystem.theme.TextWColor
import core.designsystem.theme.bigLogo

@Composable
fun SplashScreen(
    navigateToStart: () -> Unit,
    navigateToScheduleList: () -> Unit,
    showToast: (String) -> Unit,
    viewModel: SplashViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        viewModel.fetchIsCorrectAccessToken(
            navigateToStart = navigateToStart,
            navigateToScheduleList = navigateToScheduleList
        ) { showToast(it) }
    }

    Box(modifier = Modifier.fillMaxSize().background(TextWColor)) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = MyIconPack.IcMainlogo,
                contentDescription = "ic_mainlogo",
                modifier = Modifier.size(150.dp)
            )
            Text( text = stringResource(id = com.busschedule.common.R.string.app_name), style = bigLogo )
        }
    }
}