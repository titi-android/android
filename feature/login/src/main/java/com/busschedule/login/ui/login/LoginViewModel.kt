package com.busschedule.login.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busschedule.domain.repository.TokenRepository
import com.busschedule.domain.usecase.fcm.PostFCMTokenUseCase
import com.busschedule.domain.usecase.user.LoginUseCase
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val postFCMTokenUseCase: PostFCMTokenUseCase,
    private val tokenRepository: TokenRepository
) : ViewModel() {
    private val _inputId = MutableStateFlow("")
    val inputId: StateFlow<String> = _inputId.asStateFlow()

    private val _inputPw = MutableStateFlow("")
    val inputPw: StateFlow<String> = _inputPw.asStateFlow()

    fun updateInputId(input: String) {
        _inputId.update { input }
    }

    fun updateInputPw(input: String) {
        _inputPw.update { input }
    }

    private suspend fun initFCMToken() {
        val token = Firebase.messaging.token.await()
        fetchPostFCMToken(token)
    }

    fun fetchLogin(
        id: String,
        pw: String,
        autoLoginState: Boolean,
        showToast: (String) -> Unit,
        navigationToScheduleList: () -> Unit,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            loginUseCase(name = id, password = pw).onSuccess {
                val FCMTokenJob = launch { initFCMToken() }
                FCMTokenJob.join()
                navigationToScheduleList()
                tokenRepository.saveAutoLoginState(autoLoginState)
            }.onFailure {
                showToast(it.message!!)
                it.printStackTrace()
            }
        }
    }

    private suspend fun fetchPostFCMToken(token: String) {
        postFCMTokenUseCase(token)
    }
}