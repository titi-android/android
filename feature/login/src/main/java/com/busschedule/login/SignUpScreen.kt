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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.busschedule.util.constant.Constants
import com.example.connex.ui.domain.ApplicationState

@Composable
fun SignUpScreen(appState: ApplicationState, loginViewModel: LoginViewModel = hiltViewModel()) {

    val signupUiState by loginViewModel.signupUiState.collectAsStateWithLifecycle(LoginUiState())
    val context = LocalContext.current

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Center
        ) {
            TextField(
                value = signupUiState.inputId,
                onValueChange = { loginViewModel.updateSignupInputId(it) },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                placeholder = { Text(text = "아이디") }
            )
            TextField(
                value = signupUiState.inputPw,
                onValueChange = { loginViewModel.updateSignupInputPw(it) },
                modifier = Modifier.fillMaxWidth(),
                maxLines = 1,
                placeholder = { Text(text = "비밀번호") }
            )
        }
        Button(onClick = {
//            loginViewModel.fetchSignup(
//                signupUiState.inputId,
//                signupUiState.inputPw
//            ) {
//                Toast.makeText(context, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show()
//            }
        }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "회원가입")
        }
        Button(
            onClick = { appState.navigate(Constants.LOGIN_ROUTE) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "로그인 하러 가기")
        }

    }
}