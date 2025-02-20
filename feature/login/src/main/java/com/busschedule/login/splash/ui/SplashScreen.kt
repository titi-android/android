package com.busschedule.login.splash.ui

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
import androidx.compose.ui.unit.dp
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcMainlogo
import core.designsystem.theme.TextWColor
import core.designsystem.theme.bigLogo
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navigateToStart: () -> Unit) {


    LaunchedEffect(Unit) {
        delay(1500L)
        navigateToStart()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(TextWColor)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = MyIconPack.IcMainlogo,
                contentDescription = "ic_mainlogo",
                modifier = Modifier.size(150.dp)
            )
            Text(text = "떠나링", style = bigLogo)
        }
    }
}