package com.busschedule.register.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.runtime.derivedStateOf
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
import com.busschedule.model.BusStop
import com.busschedule.model.BusType
import com.busschedule.register.RegisterBusScheduleViewModel
import com.busschedule.register.component.BusInputDialog
import com.busschedule.register.component.CheckBoxIcon
import com.busschedule.register.component.SearchTextField
import com.busschedule.register.entity.AddBusDialogUiState
import com.busschedule.register.entity.SelectedBusUI
import com.busschedule.util.state.ApplicationState
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import core.designsystem.component.HeightSpacer
import core.designsystem.component.WidthSpacer
import core.designsystem.component.appbar.BackArrowAppBar
import core.designsystem.component.button.MainBottomButton
import core.designsystem.svg.MyIconPack
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
    busStop: BusStop?,
) {
    val uiState by registerBusScheduleViewModel.busStopInput.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    var isShowBottomSheet by remember { mutableStateOf(false) }
    var isShowDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        busStop?.let {
            if (it.busStop.isNotEmpty() && it.nodeId.isNotEmpty()) {
                registerBusScheduleViewModel.fetchFirstReadAllBusStop(it.region, it.busStop)
            }
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
                isShowBottomSheet = false
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
            BusesBottomSheet(
                selectedBusUi = registerBusScheduleViewModel.busStop.collectAsStateWithLifecycle().value,
                addBus = { isShowDialog = true }) {
                registerBusScheduleViewModel.addBusStopInSelectBusStopInfo { appState.popBackStackRegister() }
            }
        }
        if (isShowDialog) {
            val dialogUiState by registerBusScheduleViewModel.addBusDialogUiState.collectAsStateWithLifecycle(
                initialValue = AddBusDialogUiState()
            )
            BusInputDialog(
                uiState = dialogUiState,
                updateInput = { registerBusScheduleViewModel.updateAddBusInput(it) },
                addBus = { registerBusScheduleViewModel.addDialogAddBus { appState.showToastMsg(it) } },
                onDismissRequest = {
                    isShowDialog = false
                    registerBusScheduleViewModel.initDialogAddBus()
                },
                onCancel = { isShowDialog = false }) {
                isShowDialog = false
                registerBusScheduleViewModel.addDialogAddBusInBusStop()
                registerBusScheduleViewModel.initDialogAddBus()
            }
        }
    }

}

@Composable
fun BusCard(name: String, type: BusType, suffixIcon: @Composable () -> Unit, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 22.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = MyIconPack.IcBus,
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
            text = name,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start,
            style = mTitle.copy(Primary)
        )
        suffixIcon()
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
fun BoxScope.BusesBottomSheet(
    selectedBusUi: SelectedBusUI,
    addBus: () -> Unit,
    onCompleted: () -> Unit,
) {
    Column(
        modifier = Modifier
            .align(Alignment.BottomCenter)
            .fillMaxWidth()
            .fillMaxHeight(0.4f)
            .background(TextWColor)
            .customNavigationBarPadding(true)
    ) {
        val btnEnable by remember {
            derivedStateOf { selectedBusUi.buses.any { it.isSelected } }
        }
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
                    Text(text = selectedBusUi.busStop, style = mTitle.copy(Primary))
                }
            }
            items(items = selectedBusUi.buses, key = { it.name }) {
                BusCard(
                    name = "${it.name}번",
                    type = it.type,
                    suffixIcon = { CheckBoxIcon(it.isSelected) }) { it.isSelected = !it.isSelected }
            }
            item {
                BusCard(
                    name = "버스 번호 추가하기",
                    type = BusType.지정,
                    suffixIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Add,
                            contentDescription = "ic_add",
                            tint = Primary,
                            modifier = Modifier.size(24.dp)
                        )
                    }) { addBus() }
            }
        }
        MainBottomButton(text = "완료", enabled = btnEnable) { onCompleted() }
    }
}

fun Modifier.customNavigationBarPadding(state: Boolean) =
    if (state) this.navigationBarsPadding() else this