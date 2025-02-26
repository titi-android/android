package com.busschedule.setting.ui.setting.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.busschedule.setting.ui.setting.SettingViewModel
import com.busschedule.util.state.ApplicationState
import core.designsystem.component.HeightSpacer
import core.designsystem.component.WidthSpacer
import core.designsystem.component.appbar.BackArrowAppBar
import core.designsystem.component.dialog.CloseDialog
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcBan
import core.designsystem.svg.myiconpack.IcForwardArrow
import core.designsystem.svg.myiconpack.IcTalk
import core.designsystem.theme.Primary
import core.designsystem.theme.TextMColor
import core.designsystem.theme.TextWColor
import core.designsystem.theme.mTitle
import core.designsystem.theme.rFooter

@Composable
fun SettingScreen(appState: ApplicationState, viewModel: SettingViewModel = hiltViewModel()) {
    var isShowUserDeleteDialog by remember { mutableStateOf(false) }

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
            SettingContentCard(icon = MyIconPack.IcTalk, onClick = { appState.navigateToAsk() }) {
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
            SettingContentCard(
                icon = MyIconPack.IcBan,
                onClick = { isShowUserDeleteDialog = true }) {
                Text(text = "회원 탈퇴", style = mTitle.copy(Primary))
            }
        }
        if (isShowUserDeleteDialog) {
            CloseDialog(
                title = "‘회원탈퇴’ 하시겠습니까?",
                content = "탈퇴시, 유저 정보는 복구 되지 않습니다!",
                onDismissRequest = { isShowUserDeleteDialog = false }) {
                viewModel.fetchDeleteUser(showToast = {appState.showToastMsg(it)}) {
                    isShowUserDeleteDialog = false
                    appState.popBackStackStart()
                }
            }
        }
    }

}


@Composable
fun WhiteRoundedCard(
    padding: PaddingValues,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        colors = CardDefaults.cardColors(containerColor = TextWColor, contentColor = TextMColor),
        onClick = { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}

@Composable
fun SettingContentCard(
    icon: ImageVector,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit,
) {
    WhiteRoundedCard(
        padding = PaddingValues(horizontal = 16.dp),
        onClick = { onClick() }) {
        Row(modifier = Modifier.weight(1f)) {
            Icon(
                imageVector = icon,
                contentDescription = "ic_forward",
                modifier = Modifier.size(24.dp),
                tint = Primary
            )
            WidthSpacer(width = 16.dp)
            content()
        }

        Icon(
            imageVector = MyIconPack.IcForwardArrow,
            contentDescription = "ic_forward",
            modifier = Modifier.size(24.dp),
            tint = Primary
        )
    }
}

