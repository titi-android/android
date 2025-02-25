package com.busschedule.setting.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busschedule.domain.usecase.user.DeleteUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val deleteUserUseCase: DeleteUserUseCase
): ViewModel() {

    fun fetchDeleteUser(showToast: (String) -> Unit, navigateToStart: () -> Unit) {
        viewModelScope.launch {
            deleteUserUseCase().onSuccess {
                showToast("회원 탈퇴 성공")
                navigateToStart()
            }.onFailure { showToast(it.message!!) }
        }
    }

}