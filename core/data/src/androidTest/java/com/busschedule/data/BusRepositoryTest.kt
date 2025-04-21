package com.busschedule.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.busschedule.data.local.datastore.TokenManager
import com.busschedule.domain.repository.BusRepository
import com.busschedule.domain.repository.UserRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
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
class BusRepositoryTest {
    //테스트 스레드 풀 지정을 위한 디스패처
    private val dispatcher = StandardTestDispatcher()
    //테스트에서 사용할 코루틴 스코프
    private val testScope = TestScope(dispatcher)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: BusRepository

    @Inject
    lateinit var userRepository: UserRepository

    @Inject
    lateinit var tokenManager: TokenManager

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
    //  모란 고개에 240번 버스가 경유 하는지 테스트
    fun readAllBus() = testScope.runTest {

        // give
        val cityName = "성남시"
        val busStopId = "GGB204000087" // 모란 고개

        // when
        val result = repository.readAllBus(cityName = cityName, busStopId = busStopId)
        println("result: $result")

        // then
        assertNotNull(result.find { it.name == "240" })

    }

    @Test
    fun isExistAccessTokenInDataStore() = testScope.runTest {
        val t = userRepository.login(name = "test", password = "test")
        val accessToken = tokenManager.getAccessToken().first()
        println("accessToken: $accessToken")
//        assertNull(accessToken)
//        assertNotNull(accessToken)
    }

//    @Test
//    fun login() = testScope.runTest {
//        userRepository.login(name = "test", password = "0000")
//    }
}