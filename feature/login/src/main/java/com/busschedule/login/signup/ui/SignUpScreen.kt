package com.busschedule.login.signup.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.busschedule.login.entity.SignupUiState
import com.busschedule.login.signup.SignUpViewModel
import com.busschedule.util.state.ApplicationState
import core.designsystem.component.appbar.BackArrowAppBar
import core.designsystem.component.button.MainBottomButton
import core.designsystem.component.textfield.PrimaryOutlineTextField

@Composable
fun SignUpScreen(appState: ApplicationState, viewModel: SignUpViewModel = hiltViewModel()) {

    val uiState by viewModel.signupUiState.collectAsStateWithLifecycle(SignupUiState())
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    val isBtnEnable by remember {
        derivedStateOf { uiState.inputId.isNotEmpty() && uiState.inputPw.isNotEmpty() && uiState.inputCheckPw.isNotEmpty() && (uiState.inputPw == uiState.inputCheckPw) }
    }
    val isCheckPwBtnError by remember {
        derivedStateOf { uiState.inputCheckPw.isNotEmpty() && (uiState.inputPw != uiState.inputCheckPw) }
    }

    val fetchSignup = {
        viewModel.fetchSignup(
            id = uiState.inputId,
            pw = uiState.inputPw,
            showToast = { Toast.makeText(context, it, Toast.LENGTH_SHORT).show() }) { appState.navigateToLogin() }
    }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        BackArrowAppBar(title = "회원가입") { appState.popBackStack() }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(scrollState)
                .imePadding()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            PrimaryOutlineTextField(
                value = uiState.inputId,
                onValueChange = { viewModel.updateInputId(it) },
                placeholder = "아이디",
                errorText = "",
                keyboardActions = { focusManager.moveFocus(FocusDirection.Down) }
            )
            PrimaryOutlineTextField(
                value = uiState.inputPw,
                onValueChange = { viewModel.updateInputPw(it) },
                placeholder = "비밀번호",
                errorText = "",
                keyboardActions = { focusManager.moveFocus(FocusDirection.Down) }
            )
            PrimaryOutlineTextField(
                value = uiState.inputCheckPw,
                onValueChange = { viewModel.updateInputCheckPw(it) },
                placeholder = "비밀번호 확인",
                errorText = "비밀번호가 일치하지 않습니다.",
                isError = isCheckPwBtnError,
                keyboardActions = { focusManager.clearFocus() }
            )
        }
        MainBottomButton(text = "완료", enabled = isBtnEnable) { fetchSignup() }
    }
}