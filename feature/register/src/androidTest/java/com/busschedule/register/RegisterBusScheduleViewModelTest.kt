package com.busschedule.register

import androidx.lifecycle.SavedStateHandle
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.busschedule.domain.repository.RecentlySearchBusStopRepository
import com.busschedule.domain.repository.TempSaveScheduleRepository
import com.busschedule.domain.usecase.bus.ReadAllBusOfBusStopUseCase
import com.busschedule.domain.usecase.busstop.ReadAllBusStopUseCase
import com.busschedule.domain.usecase.schedule.PostScheduleUseCase
import com.busschedule.domain.usecase.schedule.PutScheduleUseCase
import com.busschedule.domain.usecase.schedule.ReadScheduleUseCase
import com.busschedule.model.exception.BusStopInfo
import com.busschedule.register.model.KakaoMapObject
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class RegisterBusScheduleViewModelTest {
    //테스트 스레드 풀 지정을 위한 디스패처
    private val dispatcher = StandardTestDispatcher()

    //테스트에서 사용할 코루틴 스코프
    private val testScope = TestScope(dispatcher)

    lateinit var viewModel: RegisterBusScheduleViewModel
    lateinit var postScheduleUseCase: PostScheduleUseCase
    lateinit var readAllBusStopUseCase: ReadAllBusStopUseCase
    lateinit var readAllBusOfBusStopUseCase: ReadAllBusOfBusStopUseCase
    lateinit var readScheduleUseCase: ReadScheduleUseCase
    lateinit var putScheduleUseCase: PutScheduleUseCase
    lateinit var recentlySearchBusStopRepository: RecentlySearchBusStopRepository
    lateinit var tempSaveScheduleRepository: TempSaveScheduleRepository

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        postScheduleUseCase = PostScheduleUseCase(FakeScheduleRepository())
//        readAllBusStopUseCase = ReadAllBusStopUseCase(FakeBusStopRepository())
        readAllBusStopUseCase = mockk()
        readAllBusOfBusStopUseCase = ReadAllBusOfBusStopUseCase(FakeBusRepository())
        readScheduleUseCase = ReadScheduleUseCase(FakeScheduleRepository())
        putScheduleUseCase = PutScheduleUseCase(FakeScheduleRepository())
        recentlySearchBusStopRepository = FakeRecentlySearchBusStopRepository()
        tempSaveScheduleRepository = FakeTempSaveScheduleRepository()
        viewModel = RegisterBusScheduleViewModel(
            context = getApplicationContext(),
            postScheduleUseCase,
            readAllBusStopUseCase,
            readAllBusOfBusStopUseCase,
            readScheduleUseCase,
            putScheduleUseCase,
            recentlySearchBusStopRepository,
            tempSaveScheduleRepository,
//            dispatcher = dispatcher,
            savedStateHandle = SavedStateHandle()
        )

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        //테스트 실행 후 Test 스레드풀 고정 해제
        Dispatchers.resetMain()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun registerBusScheduleViewModel_inputRegionAndBusStop_returnBusStopInfoList() = runTest {
        val region = "성남시"
        val busStopName = "GGB204000087"
        coEvery { readAllBusStopUseCase(region, busStopName) } returns Result.success(
            listOf(
                BusStopInfo(
                    name = "모란고개",
                    nodeId = "GGB204000087",
                    tmX = 37.4370333,
                    tmY = 127.1285333
                )
            )
        )
        viewModel.kakaoMap = KakaoMapObject()
        viewModel.fetchReadAllBusStop(region = region, busStopNodeId = busStopName, {}) {}

        advanceUntilIdle()
        val result = viewModel.kakaoMap.getLabels()
        val expect = listOf(
            BusStopInfo(
                name = "모란고개",
                nodeId = "GGB204000087",
                tmX = 37.4370333,
                tmY = 127.1285333
            )
        )
        assertEquals(expect, result)
    }
}