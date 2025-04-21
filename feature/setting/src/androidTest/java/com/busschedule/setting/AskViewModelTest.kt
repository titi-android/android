package com.busschedule.setting

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.busschedule.domain.usecase.user.PostInquiryUseCase
import com.busschedule.setting.ui.ask.AskViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AskViewModelTest {
    //테스트 스레드 풀 지정을 위한 디스패처
    private val dispatcher = StandardTestDispatcher()
    //테스트에서 사용할 코루틴 스코프
    private val testScope = TestScope(dispatcher)

    lateinit var viewModel: AskViewModel
    lateinit var postInquiryUseCase: PostInquiryUseCase

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        postInquiryUseCase = PostInquiryUseCase(FakeUserRepository())
        viewModel = AskViewModel(postInquiryUseCase)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        //테스트 실행 후 Test 스레드풀 고정 해제
        Dispatchers.resetMain()
    }

    @Test
    fun tt() = testScope.runTest {
        viewModel.fetchPostInquiry({}, {})
        assertNotNull("")
    }

}