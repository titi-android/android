package com.busschedule.register.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.busschedule.register.entity.AddBusDialogUiState
import com.busschedule.register.ui.BusCard
import core.designsystem.component.HeightSpacer
import core.designsystem.component.WidthSpacer
import core.designsystem.component.button.MainButton
import core.designsystem.component.button.MainOutlineButton
import core.designsystem.theme.Primary
import core.designsystem.theme.TextBoxDis
import core.designsystem.theme.TextWColor
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
                modifier = Modifier.padding(
                    top = 16.dp,
                    bottom = 10.dp,
                    start = 16.dp,
                    end = 16.dp
                ).heightIn(max = 400.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LazyColumn(modifier = Modifier.weight(1f)) {
                    stickyHeader {
                        BusAddTextField(
                            value = uiState.input,
                            onValueChange = { updateInput(it) }) { addBus() }
                        HeightSpacer(height = 16.dp)
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
                        MainOutlineButton(text = "취소") {
                            onDismissRequest()
                        }
                    }
                    WidthSpacer(width = 10.dp)
                    Box(modifier = Modifier.weight(1f)) {
                        MainButton(text = "확인") {
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
fun BusAddTextField(value: String, onValueChange: (String) -> Unit, addBus: () -> Unit) {
    OutlinedTextField(
        value = value,
        onValueChange = { onValueChange(it) },
        modifier = Modifier.fillMaxWidth(),
        placeholder = {
            Text(text = "버스 번호 입력", style = rTextBox.copy(TextBoxDis))
        },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color(0xFFD9D9D9),
            unfocusedBorderColor = Color(0xFFD9D9D9),
            focusedContainerColor = TextWColor,
            unfocusedContainerColor = TextWColor,
        ),
        textStyle = rTextBox.copy(TextBoxDis),
        trailingIcon = {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "ic_add",
                tint = Primary,
                modifier = Modifier.clickable { addBus() })
        }
    )
}