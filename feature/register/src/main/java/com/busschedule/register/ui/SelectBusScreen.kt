package com.busschedule.register.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsBus
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.busschedule.domain.model.response.schedule.BusInfo
import com.busschedule.register.RegisterBusScheduleViewModel
import com.busschedule.register.component.SearchTextField
import com.busschedule.register.entity.SelectBusUiState
import com.example.connex.ui.domain.ApplicationState
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.LatLng
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import com.kakao.vectormap.camera.CameraUpdateFactory
import core.designsystem.component.HeightSpacer
import core.designsystem.component.WidthSpacer
import core.designsystem.component.appbar.BackArrowAppBar
import core.designsystem.theme.BackgroundColor

@Composable
fun SelectBusScreen(
    appState: ApplicationState,
    registerBusScheduleViewModel: RegisterBusScheduleViewModel = hiltViewModel(),
) {
    val uiState by registerBusScheduleViewModel.selectBusUiState.collectAsStateWithLifecycle(
        SelectBusUiState()
    )
    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundColor)
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        BackArrowAppBar(title = "버스 정류장 검색") { appState.popBackStack() }
        SearchTextField(
            value = uiState.input,
            onValueChange = { registerBusScheduleViewModel.updateBusStopInput(it) },
            placeholder = "버스 정류장 검색") {
            registerBusScheduleViewModel.fetchReadAllBusStop(uiState.input) { appState.showToastMsg(it) }
        }
        HeightSpacer(height = 16.dp)
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = {context ->
                mapView.apply { ->
                    mapView.start(
                        object : MapLifeCycleCallback() {
                            override fun onMapDestroy() {
                                appState.showToastMsg("지도를 불러오는데 실패했습니다.")
                            }

                            override fun onMapError(e: Exception?) {
                                appState.showToastMsg("지도를 불러오는 중 알 수 없는 에러가 발생했습니다.\n onMapError: $e")
                                Log.d("kakao", "error: $e")
                            }

                        },
                        object : KakaoMapReadyCallback() {
                            override fun onMapReady(kakaoMap: KakaoMap) {
                                val cameraUpdate = CameraUpdateFactory.newCenterPosition(LatLng.from(37.5665, 126.9780))
//                                // 지도에 표시할 라벨의 스타일 설정
//                                val style = kakaoMap.labelManager?.addLabelStyles(LabelStyles.from(
//                                    LabelStyle.from(원하는 라벨 drawble파일을 적용)))
//
//                                // 라벨 옵션을 설정하고 위치와 스타일을 적용
//                                val options = LabelOptions.from(LatLng.from(locationY, locationX)).setStyles(style)
//
//                                // KakaoMap의 labelManager에서 레이어를 가져옴
//                                val layer = kakaoMap.labelManager?.layer
//
                                // 카메라를 지정된 위치로 이동
                                kakaoMap.moveCamera(cameraUpdate)
//
//                                // 지도에 라벨을 추가
//                                layer?.addLabel(options)
                            }
//                            override fun getPosition(): LatLng {
//                                // 현재 위치를 반환
//                                return LatLng.from(locationY, locationX)
//                            }
                        }
                    )
                }
            })
//        val lazyListState = rememberLazyListState()
//        LazyColumn(
//            state = lazyListState,
//            modifier = Modifier
//                .weight(1f)
//                .fillMaxWidth(),
//            contentPadding = PaddingValues(vertical = 16.dp)
//        ) {
//            items(items = uiState.busStop, key = { it.busStop.nodeId }) {
//                BusStopCard(
//                    busStopName = it.busStop.name,
//                    latitude = it.busStop.tmX,
//                    longitude = it.busStop.tmY,
//                    busInfoList = emptyList()
//                )
//            }
//        }
//        HeightSpacer(height = 16.dp)
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp)
//        ) {
//            MainButton(
//                modifier = Modifier.padding(horizontal = 16.dp),
//                text = "완료",
//                enabled = true
//            ) {
//                appState.popBackStack()
//            }
//        }
    }
}

@Composable
fun BusStopCard(
    busStopName: String,
    latitude: Double,
    longitude: Double,
    busInfoList: List<BusInfo>,
) {
    var isShowDropDownMenu by remember { mutableStateOf(false) }
    var dropDownIcon =
        if (isShowDropDownMenu) Icons.Rounded.KeyboardArrowDown else Icons.Rounded.KeyboardArrowUp
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = busStopName)
            Icon(
                imageVector = dropDownIcon,
                contentDescription = "ic_dropdown",
                tint = Color(0xFF4D4D4D),
                modifier = Modifier.clickable { isShowDropDownMenu = !isShowDropDownMenu })
        }
        if (isShowDropDownMenu) {
            // TODO: 지도
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.64f)
                    .background(Color.Red)
            )
            BusCard()
            BusCard()
        }
    }

}

@Composable
fun BusCard() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.DirectionsBus,
            contentDescription = "ic_bus",
            modifier = Modifier.size(24.dp)
        )
        WidthSpacer(width = 16.dp)
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.SpaceEvenly) {
            Text(text = "306번")
            Text(text = "배차간격 5분")
        }
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = "ic_add",
            tint = Color(0xFF4D4D4D),
            modifier = Modifier.size(24.dp)
        )
    }
}