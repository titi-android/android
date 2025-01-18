package com.busschedule.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.busschedule.util.constant.Constants
import com.example.connex.ui.domain.ApplicationState

@Composable
fun LoginScreen(appState: ApplicationState, loginViewModel: LoginViewModel = hiltViewModel()) {

    val loginUiState by loginViewModel.loginUiState.collectAsStateWithLifecycle(LoginUiState())

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = loginUiState.inputId,
                onValueChange = { loginViewModel.updateInputId(it) },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                placeholder = { Text(text = "아이디") }
            )
            TextField(
                value = loginUiState.inputPw,
                onValueChange = { loginViewModel.updateInputPw(it) },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                placeholder = { Text(text = "비밀번호") }
            )
        }
        Button(onClick = { appState.navigate(Constants.SCHEDULELIST_ROUTE) }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "로그인")
        }
        Button(onClick = { appState.navigate(Constants.SIGNUP_ROUTE) }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "회원가입 하러 가기")
        }

    }
}