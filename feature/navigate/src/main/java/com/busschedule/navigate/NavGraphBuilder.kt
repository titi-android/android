package com.busschedule.navigate

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.busschedule.schedulelist.ScheduleListScreen
import com.busschedule.util.constant.Constants
import com.example.connex.ui.domain.ApplicationState

fun NavGraphBuilder.scheduleListComposable(appState: ApplicationState) {
    composable(route = Constants.SCHEDULELIST_ROUTE) { entry ->
        ScheduleListScreen(appState = appState)
    }
}