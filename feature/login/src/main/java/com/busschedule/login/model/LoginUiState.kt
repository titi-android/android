package com.busschedule.login.model

import androidx.compose.runtime.Immutable

@Immutable
data class LoginUiState(
    val inputId: String = "",
    val inputPw: String = "",
)