package com.busschedule.register.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.busschedule.register.RegisterBusScheduleViewModel
import com.busschedule.register.entity.SelectBusUiState
import com.example.connex.ui.domain.ApplicationState

@Composable
fun SelectBusScreen(
    appState: ApplicationState,
    registerBusScheduleViewModel: RegisterBusScheduleViewModel = hiltViewModel(),
) {
    val uiState by registerBusScheduleViewModel.selectBusUiState.collectAsStateWithLifecycle(
        SelectBusUiState()
    )
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(
            value = uiState.input,
            onValueChange = { registerBusScheduleViewModel.updateBusInput(it) },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = "ic_search",
                    modifier = Modifier.clickable { registerBusScheduleViewModel.fetchReadAllBus() }
                )
            })
        val lazyListState = rememberLazyListState()
        LazyColumn(
            state = lazyListState,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(items = uiState.busList, key = { it.name }) {
                Text(text = it.name)
            }
        }
    }
}