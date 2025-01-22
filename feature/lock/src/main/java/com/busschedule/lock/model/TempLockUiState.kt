package com.busschedule.lock.model

import androidx.compose.runtime.mutableStateOf

object TempLockUiState {
    // TODO: datastore에서 값 저장하고 가져오는 로직 추가
    var message = mutableStateOf("No Messages")
    fun updateMsg(msg: String) {
        message.value = msg
    }
}