package com.busschedule.login.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busschedule.domain.usecase.user.LoginUseCase
import com.busschedule.login.entity.LoginUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    private val _inputId = MutableStateFlow("")
    val inputId: StateFlow<String> = _inputId.asStateFlow()

    private val _inputPw = MutableStateFlow("")
    val inputPw: StateFlow<String> = _inputPw.asStateFlow()

    val loginUiState = combine(inputId, inputPw) { inputId, inputPw ->
        LoginUiState(inputId = inputId, inputPw = inputPw)
    }

    fun updateInputId(input: String) {
        _inputId.update { input }
    }

    fun updateInputPw(input: String) {
        _inputPw.update { input }
    }

    fun fetchLogin(
        id: String,
        pw: String,
        showToast: (String) -> Unit,
        navigationToScheduleList: () -> Unit,
    ) {
        viewModelScope.launch {
            loginUseCase(name = id, password = pw).onSuccess {
                showToast("로그인에 성공했습니다.")
                navigationToScheduleList()
            }.onFailure {
                showToast(it.message!!)
            }
        }
    }
}