package com.busschedule.login.ui.splash

import androidx.lifecycle.ViewModel
import com.busschedule.domain.repository.TokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
) : ViewModel() {

    fun fetchIsCorrectAccessToken() {

    }
}