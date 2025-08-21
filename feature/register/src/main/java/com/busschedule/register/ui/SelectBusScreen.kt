package com.busschedule.register.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.ArrowBackIosNew
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.busschedule.model.BusStop
import com.busschedule.model.RecentlySearchBusStop
import com.busschedule.model.constant.BusType
import com.busschedule.register.RegisterBusScheduleViewModel
import com.busschedule.register.component.BusInputDialog
import com.busschedule.register.component.CheckBoxIcon
import com.busschedule.register.model.AddBusDialogUiState
import com.busschedule.register.model.SelectBusStopUiState
import com.busschedule.register.model.SelectedBusUI
import com.busschedule.util.ext.customNavigationBarPadding
import com.busschedule.util.ext.noRippleClickable
import com.busschedule.util.state.ApplicationState
import com.kakao.vectormap.KakaoMap
import com.kakao.vectormap.KakaoMapReadyCallback
import com.kakao.vectormap.MapLifeCycleCallback
import com.kakao.vectormap.MapView
import core.designsystem.component.HeightSpacer
import core.designsystem.component.WidthSpacer
import core.designsystem.component.button.MainBottomButton
import core.designsystem.component.loading.LoadingOfCoilDialog
import core.designsystem.component.textfield.SearchTextField
import core.designsystem.shadow.titiShadow
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcBus
import core.designsystem.svg.myiconpack.IcClose
import core.designsystem.theme.Background
import core.designsystem.theme.Primary
import core.designsystem.theme.TextMColor
import core.designsystem.theme.TextWColor
import core.designsystem.theme.mBody
import core.designsystem.theme.mTitle
import core.designsystem.theme.rFooter
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll


@Composable
fun SelectBusScreen(
    appState: ApplicationState,
    viewModel: RegisterBusScheduleViewModel = hiltViewModel(),
    busStop: BusStop,
) {
    val uiState by viewModel.selectBusStopUiState.collectAsStateWithLifecycle(SelectBusStopUiState())

    val context = LocalContext.current
    val mapView = remember { MapView(context) }
    var isShowBottomSheet by remember { mutableStateOf(false) }
    var isShowDialog by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(busStop.isNotBlank()) }

    LaunchedEffect(busStop) {
        listOf(async { viewModel.fetchReadAllRecentlySearchBusStop() }, async {
            if (busStop.isNotBlank()) {
                viewModel.fetchFirstReadAllBusStop(
                    busStop.region,
                    busStop.busStop,
                    changeLoadingState = { isLoading = false }
                ) { msg -> appState.showToastMsg(msg) }
            }
        }).awaitAll()
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
            .customNavigationBarPadding(true)
    ) {
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
                                    viewModel.initKakaoMap(kakaoMap)

                                kakaoMap.setOnLabelClickListener { kakaoMap, labelLayer, label ->
                                    val busStopInfo = kakaoMapObject.findBusStop(
                                        name = label.texts.first(),
                                        lat = label.position.latitude,
                                        lng = label.position.longitude
                                    )

                                    if (busStop.id == 0) {
                                        kakaoMapObject.moveCamera(
                                            label.position,
                                            isUpCamera = true
                                        )
                                        isShowBottomSheet = true
                                        viewModel.updateBusStop(
                                            region = busStop.region,
                                            busStopName = label.texts.first(),
                                            nodeId = busStopInfo.nodeId
                                        )
                                        viewModel.busStop.value.busStop
                                        return@setOnLabelClickListener false
                                    }
/*
                                    viewModel.fetchReadAllBusOfBusStop(
                                        id = busStop.id,
                                        busStopName = label.texts.first(),
                                        nodeId = busStopInfo.nodeId,
                                        hideBottomSheet = { isShowBottomSheet = true }
                                    ) { appState.showToastMsg(it) }

 */
                                    kakaoMapObject.moveCamera(label.position, isUpCamera = true)
                                    false
                                }

//                                kakaoMap.setOnMapClickListener { kakaoMap, latLng, point, poi->
//                                    Log.d("daeyoung", "latlng: $latLng")
//                                }
                            }
                        }
                    )
                }
            })
        Column(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .statusBarsPadding()
                .padding(start = 16.dp, end = 16.dp, top = 20.dp)
                .fillMaxWidth()
        ) {
            SelectBusAppBar(
                value = uiState.input,
                onValueChange = { viewModel.updateBusStopInput(it) },
                recentlyBusStopSearch = uiState.recentlySearchBusStop,
                onClickFromRecentlyBusStop = { recently ->
                    if (viewModel.isEqualBusStop(recently.search).not()) {
                        isLoading = true
                        viewModel.fetchReadAllBusStop(
                            region = recently.region,
                            busStopNodeId = recently.search,
                            changeLoadingState = { isLoading = false }
                        ) { appState.showToastMsg(it) }
                        isShowBottomSheet = false
                    }
                },
                onDeleteFromRecentlyBusStop = { viewModel.fetchDeleteRecentlySearchBusStop(it.search)},
                popBackStack = { appState.popBackStack() }) {
                isLoading = true
                viewModel.fetchReadAllBusStop(
                    region = busStop.region,
                    busStopNodeId = uiState.input,
                    changeLoadingState = { isLoading = false }
                ) { appState.showToastMsg(it) }
                isShowBottomSheet = false
            }
        }

        if (isShowBottomSheet) {
            if (busStop.id == 0) {
                MainBottomButton(
                    modifier = Modifier.align(Alignment.BottomCenter),
                    text = "완료"
                ) { viewModel.completeOfArriveBusStop(region = busStop.region) { appState.popBackStackRegister() } }
            } else {
                BusesBottomSheet(
                    selectedBusUi = viewModel.busStop.collectAsStateWithLifecycle().value,
                    addBus = { isShowDialog = true }) {
                    /*
                    viewModel.addBusStopInSelectBusStopInfo(id = busStop.id) {
                        appState.popBackStackRegister()
                    }
                     */
                }
            }

        }
        if (isShowDialog) {
            val dialogUiState by viewModel.addBusDialogUiState.collectAsStateWithLifecycle(
                initialValue = AddBusDialogUiState()
            )
            BusInputDialog(
                uiState = dialogUiState,
                updateInput = { viewModel.updateAddBusInput(it) },
                addBus = { viewModel.addDialogAddBus { appState.showToastMsg(it) } },
                onDismissRequest = {
                    isShowDialog = false
                    viewModel.initDialogAddBus()
                },
                onCancel = { isShowDialog = false }) {
                isShowDialog = false
                viewModel.addDialogAddBusInBusStop()
                viewModel.initDialogAddBus()
            }
        }
        if (isLoading) {
            LoadingOfCoilDialog()
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
    ) {
        val btnEnable by remember(selectedBusUi.buses) {
            derivedStateOf { selectedBusUi.buses.any { it.isSelected } }
        }
        val lazyListState = rememberLazyListState()
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.weight(1f),
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
            items(items = selectedBusUi.buses) {
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

@Composable
fun SelectBusAppBar(
    value: String,
    onValueChange: (String) -> Unit,
    recentlyBusStopSearch: List<RecentlySearchBusStop>,
    onClickFromRecentlyBusStop: (RecentlySearchBusStop) -> Unit,
    onDeleteFromRecentlyBusStop: (RecentlySearchBusStop) -> Unit,
    popBackStack: () -> Unit,
    onSearch: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        IconButton(
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = TextWColor,
                contentColor = Primary
            ),
            modifier = Modifier.titiShadow(
                color = Color.Black.copy(alpha = 0.1f),
                blurRadius = 4.dp,
                borderRadius = 100.dp
            ),
            onClick = { popBackStack() }) {
            Icon(
                imageVector = Icons.Rounded.ArrowBackIosNew,
                contentDescription = "ic_back_arrow",
                modifier = Modifier.size(16.dp)
            )
        }

        Column(modifier = Modifier.padding(start = 8.dp)) {
            SearchTextField(
                modifier = Modifier
                    .titiShadow(
                        color = Color.Black.copy(alpha = 0.18f),
                        blurRadius = 4.dp,
                        borderRadius = 12.dp
                    ),
                value = value,
                onValueChange = { onValueChange(it) },
                placeholder = "버스 정류장 검색"
            ) { onSearch() }
            HeightSpacer(height = 4.dp)
            LazyRow(
                modifier = Modifier,
                contentPadding = PaddingValues(horizontal = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(items = recentlyBusStopSearch.take(3)) { recently ->
                    RecentSearchCard(
                        text = recently.search,
                        onClick = { onClickFromRecentlyBusStop(recently) }
                    ) { onDeleteFromRecentlyBusStop(recently) }
                }
            }
        }
    }
}

@Composable
fun RecentSearchCard(text: String, onClick: () -> Unit, onDelete: () -> Unit) {
    Card(
        colors = CardDefaults.cardColors(contentColor = TextMColor, containerColor = TextWColor),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.titiShadow(
            color = Color.Black.copy(alpha = 0.05f),
            blurRadius = 8.dp,
            borderRadius = 8.dp,
        ),
        onClick = { onClick() }) {
        Row(modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(text = text, style = mBody)
            WidthSpacer(width = 4.dp)
            Icon(
                imageVector = MyIconPack.IcClose,
                contentDescription = "ic_close",
                modifier = Modifier
                    .size(12.dp)
                    .noRippleClickable { onDelete() }
            )
        }
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = 0xFFFFffff)
fun TTT(modifier: Modifier = Modifier) {
    Box(modifier = Modifier) {
        Row {
            RecentSearchCard("asd", {}, {})
            WidthSpacer(width = 4.dp)
            RecentSearchCard("asd", {}, {})
            WidthSpacer(width = 4.dp)
            RecentSearchCard("asd", {}, {})
        }
    }
}