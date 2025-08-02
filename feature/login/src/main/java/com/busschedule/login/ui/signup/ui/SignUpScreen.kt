package com.busschedule.login.ui.signup.ui

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import com.busschedule.login.ui.signup.SignUpViewModel
import com.busschedule.util.state.ApplicationState
import core.designsystem.component.HeightSpacer
import core.designsystem.component.appbar.BackArrowAppBar
import core.designsystem.component.button.MainBottomButton
import core.designsystem.component.textfield.PasswordOutlineTextField
import core.designsystem.component.textfield.PrimaryOutlineTextField
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcForwardArrow
import core.designsystem.theme.Primary
import core.designsystem.theme.mTitle

@Composable
fun SignUpScreen(appState: ApplicationState, viewModel: SignUpViewModel = hiltViewModel()) {

    val inputIdUiState by viewModel.inputId.collectAsStateWithLifecycle()
    val inputPwUiState by viewModel.inputPw.collectAsStateWithLifecycle()
    val inputCheckPwUiState by viewModel.inputCheckPw.collectAsStateWithLifecycle()

    val updateInputId = remember { { id: String -> viewModel.updateInputId(id) } }
    val updateInputPw = remember { { pw: String -> viewModel.updateInputPw(pw) } }
    val updateInputCheckPw = remember { { pw: String -> viewModel.updateInputCheckPw(pw) } }

    val focusManager = LocalFocusManager.current

    val moveFocus: () -> Unit = remember { { focusManager.moveFocus(FocusDirection.Down) } }
    val clearFocus = remember { { focusManager.clearFocus() } }

    val context = LocalContext.current

    var isCheckPrivacyPolicy by remember { mutableStateOf(false) }
    var isShowPrivacyPolicy by remember { mutableStateOf(false) }
    val isBtnEnable by remember(inputIdUiState, inputPwUiState, inputCheckPwUiState, isCheckPrivacyPolicy) {
        derivedStateOf { viewModel.isAllNotEmptyInput() && viewModel.isEqualPwAndCheckPw() && isCheckPrivacyPolicy }
    }

    val isCheckPwBtnError by remember(inputPwUiState, inputCheckPwUiState) {
        derivedStateOf { inputPwUiState.isNotEmpty() && viewModel.isNotEqualPwAndCheckPw() }
    }

    val fetchSignup = remember {
        {
            viewModel.fetchSignup(
                id = inputIdUiState,
                pw = inputPwUiState,
                showToast = {
                    Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                }) { appState.navigateToLogin() }
        }
    }
    val scrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (isShowPrivacyPolicy) {
                PrivacyPolicyDialog(popBackStack = { isShowPrivacyPolicy = false }) {
                    isCheckPrivacyPolicy = true
                    isShowPrivacyPolicy = false
                }
            }
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
                    value = inputIdUiState,
                    onValueChange = updateInputId,
                    placeholder = "아이디",
                    errorText = "",
                    keyboardActions = moveFocus
                )
                PasswordOutlineTextField(
                    value = inputPwUiState,
                    onValueChange = updateInputPw,
                    placeholder = "비밀번호",
                    errorText = "",
                    keyboardActions = moveFocus
                )
                PasswordOutlineTextField(
                    value = inputCheckPwUiState,
                    onValueChange = updateInputCheckPw,
                    placeholder = "비밀번호 확인",
                    errorText = "비밀번호가 일치하지 않습니다.",
                    isError = isCheckPwBtnError,
                    keyboardActions = clearFocus
                )
                HeightSpacer(height = 32.dp)
                CheckBoxContainer(
                    isCheck = isCheckPrivacyPolicy,
                    onCheckedChange = { isCheckPrivacyPolicy = it },
                    text = "회원가입 및 이용약관에 동의하겠습니까?"
                )

                HeightSpacer(height = 16.dp)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { isShowPrivacyPolicy = true }
                        .padding(16.dp)
                ) {
                    Text(
                        text = "이용약관 확인하기",
                        style = mTitle.copy(Primary),
                        modifier = Modifier.weight(1f)
                    )
                    Icon(
                        imageVector = MyIconPack.IcForwardArrow,
                        contentDescription = "ic_forwardArrow",
                        modifier = Modifier.size(24.dp),
                        tint = Primary
                    )
                }
            }
            MainBottomButton(text = "완료", enabled = isBtnEnable) { fetchSignup() }
        }
    }
}