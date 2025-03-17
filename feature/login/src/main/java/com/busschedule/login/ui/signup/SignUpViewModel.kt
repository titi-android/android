package com.busschedule.login.ui.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busschedule.domain.usecase.user.SignupUseCase
import com.busschedule.login.model.SignupUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel  @Inject constructor(
    private val signupUseCase: SignupUseCase,
) : ViewModel() {
    private val _inputId = MutableStateFlow("")
    val inputId: StateFlow<String> = _inputId.asStateFlow()

    private val _inputPw = MutableStateFlow("")
    val inputPw: StateFlow<String> = _inputPw.asStateFlow()

    private val _inputCheckPw = MutableStateFlow("")
    val inputCheckPw: StateFlow<String> = _inputCheckPw.asStateFlow()

    val signupUiState = combine(inputId, inputPw, inputCheckPw) { inputId, inputPw, inputCheckPw ->
        SignupUiState(inputId = inputId, inputPw = inputPw, inputCheckPw = inputCheckPw)
    }

    fun updateInputId(input: String) {
        _inputId.update { input }
    }

    fun updateInputPw(input: String) {
        _inputPw.update { input }
    }

    fun updateInputCheckPw(input: String) {
        _inputCheckPw.update { input }
    }

    fun fetchSignup(id: String, pw: String, showToast: (String) -> Unit, navigateToLogin: () -> Unit) {
        viewModelScope.launch {
            signupUseCase(name = id, password = pw).onSuccess {
                showToast("회원가입에 성공했습니다.")
                navigateToLogin()
            }.onFailure {
                showToast(it.message!!)
            }
        }
    }
}