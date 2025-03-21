package com.busschedule.setting.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busschedule.domain.usecase.fcm.DeleteFCMTokenUseCase
import com.busschedule.domain.usecase.fcm.PostFCMTokenUseCase
import com.busschedule.domain.usecase.user.DeleteUserUseCase
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val deleteUserUseCase: DeleteUserUseCase,
    private val postFCMTokenUseCase: PostFCMTokenUseCase,
    private val deleteFCMTokenUseCase: DeleteFCMTokenUseCase,
) : ViewModel() {

    private val _isPushNotifyChecked = MutableStateFlow(false)
    val isPushNotifyChecked: StateFlow<Boolean> = _isPushNotifyChecked.asStateFlow()

    private fun updatePushNotifyChecked(state: Boolean) {
        _isPushNotifyChecked.update { state }
    }

    fun fetchDeleteUser(showToast: (String) -> Unit, navigateToStart: () -> Unit) {
        viewModelScope.launch {
            deleteUserUseCase().onSuccess {
                showToast("회원 탈퇴 성공")
                navigateToStart()
            }.onFailure { showToast(it.message!!) }
        }
    }

    fun fetchDeleteFCMToken(showToast: (String) -> Unit) {
        Firebase.messaging.deleteToken().addOnCompleteListener {
            if (it.isSuccessful.not()) return@addOnCompleteListener

            viewModelScope.launch {
                deleteFCMTokenUseCase().onSuccess { updatePushNotifyChecked(false) }
                    .onFailure { e ->
                        e.printStackTrace()
                        showToast(e.message!!)
                    }
            }
        }
    }

    fun fetchPostFCMToken(showToast: (String) -> Unit) {
        Firebase.messaging.token.addOnCompleteListener {
            if (it.isSuccessful.not()) return@addOnCompleteListener

            viewModelScope.launch {
                postFCMTokenUseCase(it.result).onSuccess { updatePushNotifyChecked(true) }
                    .onFailure { e ->
                        e.printStackTrace()
                        showToast(e.message!!)
                    }
            }
        }
    }
}