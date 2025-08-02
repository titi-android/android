package com.busschedule.login.ui.login.ui

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.busschedule.login.component.CheckBoxContainer
import com.busschedule.login.ui.login.LoginViewModel
import com.busschedule.util.state.ApplicationState
import core.designsystem.component.appbar.BackArrowAppBar
import core.designsystem.component.button.MainBottomButton
import core.designsystem.component.textfield.PasswordOutlineTextField
import core.designsystem.component.textfield.PrimaryOutlineTextField

@Composable
fun LoginScreen(appState: ApplicationState, loginViewModel: LoginViewModel = hiltViewModel()) {

    val inputIdUiState by loginViewModel.inputId.collectAsStateWithLifecycle()
    val inputPwUiState by loginViewModel.inputPw.collectAsStateWithLifecycle()

    val updateInputId = remember { {id:String -> loginViewModel.updateInputId(id)} }
    val updateInputPw = remember { {pw:String -> loginViewModel.updateInputPw(pw)} }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val clearFocus = remember { {focusManager.clearFocus()} }
    val moveFocus: () -> Unit = remember { {focusManager.moveFocus(FocusDirection.Down)} }
    val isBtnEnable by remember {
        derivedStateOf { inputIdUiState.isNotEmpty() && inputPwUiState.isNotEmpty() }
    }
    var isAuthLogin by remember { mutableStateOf(false) }

    val fetchLogin = remember {
        {
            loginViewModel.fetchLogin(
                id = inputIdUiState,
                pw = inputPwUiState,
                autoLoginState = isAuthLogin,
                showToast = {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }) { appState.navigateToScheduleList() }
        }
    }
    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        BackArrowAppBar(title = "로그인") { appState.popBackStack() }
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
                value = inputIdUiState,
                onValueChange = updateInputId,
                placeholder = "아이디",
                errorText = "",
                isError = false,
                keyboardActions = moveFocus
            )
            PasswordOutlineTextField(
                value = inputPwUiState,
                onValueChange = updateInputPw,
                placeholder = "비밀번호",
                errorText = "",
                isError = false,
                keyboardActions = clearFocus
            )

            CheckBoxContainer(isCheck = isAuthLogin, onCheckedChange = { isAuthLogin = it }, text = "자동 로그인")
        }
        MainBottomButton(text = "완료", enabled = isBtnEnable) { fetchLogin() }
    }
}
