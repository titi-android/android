package com.busschedule.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busschedule.domain.model.ApiState
import com.busschedule.domain.model.LoginUser
import com.busschedule.domain.usecase.login.LoginUseCase
import com.busschedule.domain.usecase.login.SignupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.net.ConnectException
import javax.inject.Inject

data class LoginUiState(
    val inputId: String = "",
    val inputPw: String = "",
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signupUseCase: SignupUseCase,
    private val loginUseCase: LoginUseCase
) : ViewModel() {
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

    fun fetchSignup(id: String, pw: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            when (val result = signupUseCase(LoginUser(name = id, password = pw)).first()) {
                is ApiState.Error -> Log.d("daeyoung", "api 통신 에러: ${result.errMsg}")
                ApiState.Loading -> TODO()
                is ApiState.Success<*> -> result.onSuccess { onSuccess() }
                is ApiState.NotResponse -> {
                    Log.d("daeyoung", "exception: ${result.exception}, msg: ${result.message}")
                    if (result.exception is ConnectException) { }
                }
            }
        }
    }

    fun fetchLogin(id: String, pw: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            when (val result = loginUseCase(LoginUser(name = id, password = pw)).first()) {
                is ApiState.Error -> Log.d("daeyoung", "api 통신 에러: ${result.errMsg}")
                ApiState.Loading -> TODO()
                is ApiState.Success<*> -> result.onSuccess {
                    Log.d("daeyoung", "token: ${result.data}")
                    onSuccess() }
                is ApiState.NotResponse -> {
                    Log.d("daeyoung", "exception: ${result.exception}, msg: ${result.message}")
                    if (result.exception is ConnectException) { }
                }
            }
        }
    }
}