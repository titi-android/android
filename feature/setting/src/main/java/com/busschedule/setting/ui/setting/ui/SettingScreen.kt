package com.busschedule.setting.ui.setting.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.busschedule.common.constant.Constants
import com.busschedule.setting.ui.setting.SettingViewModel
import com.busschedule.setting.ui.setting.component.PushNotifyCheckingCard
import com.busschedule.setting.ui.setting.component.SettingContentCard
import com.busschedule.util.state.ApplicationState
import core.designsystem.component.HeightSpacer
import core.designsystem.component.appbar.BackArrowAppBar
import core.designsystem.component.dialog.CloseDialog
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcBan
import core.designsystem.svg.myiconpack.IcLogout
import core.designsystem.svg.myiconpack.IcNotify
import core.designsystem.svg.myiconpack.IcTalk
import core.designsystem.svg.myiconpack.IcVersionInfo
import core.designsystem.theme.Primary
import core.designsystem.theme.TextMColor
import core.designsystem.theme.mTitle
import core.designsystem.theme.rFooter

@Composable
fun SettingScreen(appState: ApplicationState, viewModel: SettingViewModel = hiltViewModel()) {
    var isShowUserDeleteDialog by remember { mutableStateOf(false) }
    val isPushNotifyChecked = viewModel.isPushNotifyChecked.collectAsStateWithLifecycle().value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BackArrowAppBar(title = "설정") { appState.popBackStack() }
            HeightSpacer(height = 16.dp)
            PushNotifyCheckingCard(
                icon = MyIconPack.IcNotify,
                isCheck = isPushNotifyChecked,
                onClickOnCheck = { viewModel.fetchDeleteFCMToken { appState.showToastMsg(it) } },
                onClickOffCheck = { viewModel.fetchPostFCMToken { appState.showToastMsg(it) } }) {
                Column {
                    Text(text = "푸시 알림", style = mTitle.copy(Primary))
                    HeightSpacer(height = 4.dp)
                    Text(
                        text = "모바일 앱에서 스케줄 알림에 대한 푸시 알림을 받습니다.", style = rFooter.copy(TextMColor)
                    )
                }
            }
            HeightSpacer(height = 16.dp)
            SettingContentCard(
                icon = MyIconPack.IcLogout,
                hideForwardIcon = true,
                onClick = { isShowUserDeleteDialog = true }) {
                Text(text = "로그아웃", style = mTitle.copy(Primary))
            }
            HeightSpacer(height = 16.dp)
            SettingContentCard(
                icon = MyIconPack.IcBan,
                hideForwardIcon = true,
                onClick = { isShowUserDeleteDialog = true }) {
                Text(text = "회원 탈퇴", style = mTitle.copy(Primary))
            }
            HeightSpacer(height = 16.dp)
            SettingContentCard(icon = MyIconPack.IcVersionInfo) {
                Column {
                    Text(text = "버전 정보", style = mTitle.copy(Primary))
                    HeightSpacer(height = 4.dp)
                    Text(
                        text = Constants.VERSION, style = rFooter.copy(TextMColor)
                    )
                }
            }
            HeightSpacer(height = 16.dp)
            SettingContentCard(
                icon = MyIconPack.IcTalk,
                hideForwardIcon = true,
                onClick = { appState.navigateToAsk() }) {
                Column {
                    Text(text = "개발자에게 문의하기", style = mTitle.copy(Primary))
                    HeightSpacer(height = 4.dp)
                    Text(
                        text = "오류 및 건의사항이 있을 시, 개발자 문의사항에 남겨주세요.\n" +
                                "더 나은 서비스를 위한 도움이 됩니다. ", style = rFooter.copy(TextMColor)
                    )
                }
            }
            HeightSpacer(height = 16.dp)
        }
        if (isShowUserDeleteDialog) {
            CloseDialog(
                title = "‘회원탈퇴’ 하시겠습니까?",
                content = "탈퇴시, 유저 정보는 복구 되지 않습니다!",
                onDismissRequest = { isShowUserDeleteDialog = false }) {
                viewModel.fetchDeleteUser(showToast = { appState.showToastMsg(it) }) {
                    isShowUserDeleteDialog = false
                    appState.popBackStackStart()
                }
            }
        }
    }

}


