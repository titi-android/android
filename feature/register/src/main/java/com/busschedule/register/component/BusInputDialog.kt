package com.busschedule.register.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.busschedule.register.model.AddBusDialogUiState
import com.busschedule.register.ui.BusCard
import core.designsystem.component.HeightSpacer
import core.designsystem.component.WidthSpacer
import core.designsystem.component.button.MainButton
import core.designsystem.component.button.MainOutlineButton
import core.designsystem.svg.MyIconPack
import core.designsystem.svg.myiconpack.IcInfomationCircle
import core.designsystem.theme.ErrorColor
import core.designsystem.theme.Primary
import core.designsystem.theme.TextBoxDis
import core.designsystem.theme.TextMColor
import core.designsystem.theme.TextWColor
import core.designsystem.theme.rFooter
import core.designsystem.theme.rTextBox

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BusInputDialog(
    uiState: AddBusDialogUiState,
    updateInput: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onCancel: () -> Unit,
    addBus: () -> Unit,
    onCheck: () -> Unit,
) {
    Dialog(onDismissRequest = { onDismissRequest() }) {
        Card(
            colors = CardDefaults.cardColors(containerColor = TextWColor),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(
                        top = 16.dp,
                        bottom = 10.dp,
                        start = 16.dp,
                        end = 16.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LazyColumn(
                    modifier = Modifier
                        .wrapContentHeight()
                        .heightIn(max = 300.dp)
                ) {
                    stickyHeader {
                        BusAddTextField(
                            value = uiState.input,
                            onValueChange = { updateInput(it) }) { addBus() }
                        HeightSpacer(height = 8.dp)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = MyIconPack.IcInfomationCircle,
                                contentDescription = "ic_info_circle",
                                modifier = Modifier.size(12.dp),
                                tint = TextMColor
                            )
                            WidthSpacer(width = 4.dp)
                            Text(
                                text = "올바르지 않은 노선은 정보 제공이 제한될 수 있습니다.",
                                style = rFooter.copy(TextMColor)
                            )
                        }
                        HeightSpacer(height = 12.dp)

                    }
                    items(items = uiState.bus, key = { it.name }) {
                        BusCard(name = it.name, type = it.type, suffixIcon = {
                            CheckBoxIcon(it.isSelected)
                        }) { it.isSelected = !it.isSelected }
                    }
                }

                HeightSpacer(height = 10.dp)
                Row(modifier = Modifier.padding(top = 10.dp)) {
                    Box(modifier = Modifier.weight(1f)) {
                        MainOutlineButton(text = "취소", modifier = Modifier.fillMaxWidth()) {
                            onCancel()
                        }
                    }
                    WidthSpacer(width = 10.dp)
                    Box(modifier = Modifier.weight(1f)) {
                        MainButton(text = "확인", modifier = Modifier.fillMaxWidth()) {
                            onCheck()
                            onDismissRequest()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BusAddTextField(
    value: String,
    onValueChange: (String) -> Unit,
    addBus: () -> Unit,
) {
    var isError by remember { mutableStateOf(false) }
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = "버스 번호 입력", style = rTextBox.copy(TextBoxDis))
        },
        supportingText = if (isError) {{ Text(text = "공백은 추가할 수 없습니다.") }} else null,
        // if (isError) { Text(text = "공백은 추가할 수 없습니다.") } else { null }
        isError = isError,
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFD9D9D9),
            unfocusedBorderColor = Color(0xFFD9D9D9),
            focusedContainerColor = TextWColor,
            unfocusedContainerColor = TextWColor,
            errorSupportingTextColor = ErrorColor,
            focusedSupportingTextColor = Color.Transparent,
            unfocusedSupportingTextColor = Color.Transparent,
            cursorColor = Primary,
        ),
        maxLines = 1,
        singleLine = true,
        textStyle = rTextBox.copy(TextBoxDis),
        trailingIcon = {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "ic_add",
                tint = Primary,
                modifier = Modifier.clickable {
                    if (value.isBlank()) {
                        isError = true
                    } else {
                        isError = false
                        addBus()
                    }
                })
        }
    )
}