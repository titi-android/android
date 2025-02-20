package com.busschedule.setting.ui.ask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.busschedule.domain.usecase.user.PostInquiryUseCase
import com.busschedule.setting.ui.ask.entity.AskUIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AskViewModel @Inject constructor(
    private val postInquiryUseCase: PostInquiryUseCase
): ViewModel(){
    private val _title = MutableStateFlow("")
    val title: StateFlow<String> = _title.asStateFlow()

    private val _content = MutableStateFlow("")
    val content: StateFlow<String> = _content.asStateFlow()

    val askUIState = combine(title, content) { title, content ->
        AskUIState(
            title = title,
            content = content
        )
    }

    fun updateTitle(title: String) {
        _title.update { title }
    }

    fun updateContent(content: String) {
        _content.update { content }
    }

    fun fetchPostInquiry(showToastMsg: (String) -> Unit, navigateToSetting: () -> Unit) {
        viewModelScope.launch {
            postInquiryUseCase(title.value, content.value).onSuccess {
                showToastMsg("메일 보내기 성공")
                navigateToSetting()
            }.onFailure { showToastMsg(it.message!!) }
        }
    }
}