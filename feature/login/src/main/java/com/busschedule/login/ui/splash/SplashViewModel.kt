package com.busschedule.login.ui.splash

import android.util.Log
import androidx.lifecycle.ViewModel
import com.busschedule.domain.repository.TokenRepository
import com.busschedule.domain.usecase.user.ValidateTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val validateTokenUseCase: ValidateTokenUseCase,
    private val tokenRepository: TokenRepository
) : ViewModel() {
    suspend fun fetchIsCorrectAccessToken(navigateToStart: () -> Unit, navigateToScheduleList: () -> Unit, showToast: (String) -> Unit) {
        validateTokenUseCase().onSuccess {
            Log.d("daeyoung", "validateToken: success: $it")
            navigateToScheduleList()
        }.onFailure {
            showToast(it.message.toString())
            Log.d("daeyoung", "validateToken: fail: $it")
            navigateToStart()
        }
    }
}