package com.busschedule.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

data class LoginUiState(
    val inputId: String = "",
    val inputPw: String = ""
)

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {
    private val _inputId = MutableStateFlow("")
    val inputId: StateFlow<String> = _inputId.asStateFlow()

    private val _inputPw = MutableStateFlow("")
    val inputPw: StateFlow<String> = _inputPw.asStateFlow()

    val loginUiState = combine(inputId, inputPw) { inputId, inputPw ->
        LoginUiState(inputId = inputId, inputPw = inputPw)
    }

    private val _inputSignupId = MutableStateFlow("")
    val inputSignupId: StateFlow<String> = _inputSignupId.asStateFlow()

    private val _inputSignupPw = MutableStateFlow("")
    val inputSignupPw: StateFlow<String> = _inputSignupPw.asStateFlow()

    val signupUiState = combine(inputSignupId, inputSignupPw) { inputId, inputPw ->
        LoginUiState(inputId = inputId, inputPw = inputPw)
    }

    fun updateInputId(input: String) {
        _inputId.update { input }
    }
    fun updateInputPw(input: String) {
        _inputPw.update { input }
    }

    fun updateSignupInputId(input: String) {
        _inputSignupId.update { input }
    }
    fun updateSignupInputPw(input: String) {
        _inputSignupPw.update { input }
    }
}