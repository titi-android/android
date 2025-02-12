package com.busschedule.register.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.busschedule.register.RegisterBusScheduleViewModel
import com.busschedule.register.component.SearchTextField
import com.busschedule.register.entity.BusStopInfo
import com.busschedule.util.entity.BusType
import com.example.connex.ui.domain.ApplicationState
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import core.designsystem.component.HeightSpacer
import core.designsystem.component.WidthSpacer
import core.designsystem.component.appbar.BackArrowAppBar
import core.designsystem.component.button.MainButton
import core.designsystem.svg.IconPack
import core.designsystem.svg.myiconpack.IcBus
import core.designsystem.theme.Background
import core.designsystem.theme.Primary
import core.designsystem.theme.TextWColor
import core.designsystem.theme.mTitle
import core.designsystem.theme.rFooter


@Composable
fun SelectBusScreen(
    appState: ApplicationState,
    registerBusScheduleViewModel: RegisterBusScheduleViewModel = hiltViewModel(),
    busStopInput: String = "",
) {
    val uiState by registerBusScheduleViewModel.busStopInput.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    var isShowBottomSheet by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        if (busStopInput.isNotEmpty()) {
            registerBusScheduleViewModel.fetchFirstReadAllBusStop()
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .statusBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BackArrowAppBar(title = "버스 정류장 검색") { appState.popBackStack() }
            SearchTextField(
                value = uiState,
                onValueChange = { registerBusScheduleViewModel.updateBusStopInput(it) },
                placeholder = "버스 정류장 검색"
            ) {
                registerBusScheduleViewModel.fetchReadAllBusStop(uiState)
            }
            HeightSpacer(height = 16.dp)

            AndroidView(
                modifier = Modifier.fillMaxSize(),
                factory = { context ->
                    mapView.apply { ->

                        mapView.start(
                            object : MapLifeCycleCallback() {
                                override fun onMapDestroy() {
                                    appState.showToastMsg("지도를 불러오는데 실패했습니다.")
                                }

                                override fun onMapError(e: Exception?) {
                                    appState.showToastMsg("지도를 불러오는 중 알 수 없는 에러가 발생했습니다.\n onMapError: $e")
                                }

                            },
                            object : KakaoMapReadyCallback() {
                                override fun onMapReady(kakaoMap: KakaoMap) {
                                    val kakaoMapObject =
                                        registerBusScheduleViewModel.initKakaoMap(kakaoMap)

                                    kakaoMap.setOnLabelClickListener { kakaoMap, labelLayer, label ->
                                        val busStopInfo = kakaoMapObject.findBusStop(
                                            name = label.texts.first(),
                                            lat = label.position.latitude,
                                            lng = label.position.longitude
                                        )
                                        registerBusScheduleViewModel.fetchReadAllBusOfBusStop(
                                            busStopName = label.texts.first(),
                                            nodeId = busStopInfo.nodeId
                                        ) { isShowBottomSheet = true }
                                        kakaoMapObject.moveCamera(label.position, isUpCamera = true)
                                        false
                                    }
                                }
                            }
                        )
                    }
                })
        }
        if (isShowBottomSheet) {
            BusesBottomSheet(busStopInfo = registerBusScheduleViewModel.busStop.collectAsStateWithLifecycle().value) {
                appState.popBackStack()
            }
        }
    }

}

@Composable
fun BusCard(name: String, type: BusType) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 22.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = IconPack.IcBus,
            contentDescription = "ic_bus",
            modifier = Modifier
                .size(24.dp)
                .padding(4.dp),
            tint = type.iconColor
        )
        WidthSpacer(width = 8.dp)
        BusTypeBox(text = type.name, color = type.color)
        WidthSpacer(width = 8.dp)
        Text(
            text = "${name}번",
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start,
            style = mTitle.copy(Primary)
        )
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "ic_add",
            tint = Color(0xFF4D4D4D),
            modifier = Modifier.size(24.dp)
        )
    }
}

@Composable
fun BusTypeBox(text: String, color: Color) {
    Card(
        shape = RoundedCornerShape(4.dp),
        colors = CardDefaults.cardColors(containerColor = color, contentColor = TextWColor),
    ) {
        Box(modifier = Modifier.padding(vertical = 3.dp, horizontal = 4.5.dp)) {
            Text(text = text, style = rFooter)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BoxScope.BusesBottomSheet(busStopInfo: BusStopInfo, onCompleted: () -> Unit) {
    Column(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
            .background(TextWColor)
            .customNavigationBarPadding(true)
    ) {
        val lazyListState = rememberLazyListState()
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.weight(1f),
//            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            stickyHeader {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(TextWColor)
                        .padding(horizontal = 16.dp, vertical = 24.5.dp)
                ) {
                    Text(text = busStopInfo.busStop, style = mTitle.copy(Primary))
                }
            }
            items(items = busStopInfo.getBuses(), key = { it.name }) {
                BusCard(name = it.name, type = BusType.find(it.type))
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 10.dp)
        ) {
            MainButton(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = "완료",
                enabled = true
            ) {
                onCompleted()
            }
        }
    }
}

fun Modifier.customNavigationBarPadding(state: Boolean) = if(state) this.navigationBarsPadding() else this