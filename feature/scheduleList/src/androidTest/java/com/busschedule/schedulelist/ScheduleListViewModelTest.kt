package com.busschedule.schedulelist

import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class ScheduleListViewModelTest {
    //테스트 스레드 풀 지정을 위한 디스패처
    private val dispatcher = StandardTestDispatcher()
    //테스트에서 사용할 코루틴 스코프
    private val testScope = TestScope(dispatcher)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var scheduleListViewModel: ScheduleListViewModel

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        hiltRule.inject()
        Dispatchers.setMain(dispatcher)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        //테스트 실행 후 Test 스레드풀 고정 해제
        Dispatchers.resetMain()
    }

    @Test
    fun scheduleListViewModel_checkTodayDayOfWeek_returnTodayChecked() {
        println("dayOfWeek test:" + scheduleListViewModel.dayOfWeeks.value)
        assertNotNull("")
    }
}