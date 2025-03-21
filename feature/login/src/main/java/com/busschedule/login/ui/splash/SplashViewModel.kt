package com.busschedule.login.ui.splash

import androidx.lifecycle.ViewModel
import com.busschedule.domain.repository.TokenRepository
import com.busschedule.domain.usecase.user.ValidateTokenUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val validateTokenUseCase: ValidateTokenUseCase,
    private val tokenRepository: TokenRepository,
) : ViewModel() {
    suspend fun fetchIsCorrectAccessToken(
        navigateToStart: () -> Unit,
        navigateToScheduleList: () -> Unit,
        showToast: (String) -> Unit,
    ) {
        if ( validateAccessTokenAndAutoLogin() ) {
            navigateToStart()
            return
        }
        validateTokenUseCase().onSuccess {
            navigateToScheduleList()
        }.onFailure {
            showToast(it.message.toString())
            navigateToStart()
        }
    }

    private suspend fun validateAccessTokenAndAutoLogin(): Boolean {
        val autoLoginState = tokenRepository.getAutoLoginState().firstOrNull()
        return tokenRepository.getAccessToken().firstOrNull() == null || autoLoginState == false || autoLoginState == null
    }
}