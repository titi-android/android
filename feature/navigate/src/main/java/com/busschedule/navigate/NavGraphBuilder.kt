package com.busschedule.navigate

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.busschedule.schedulelist.ui.ScheduleListScreen
import com.busschedule.util.entity.navigation.Route
import com.example.connex.ui.domain.ApplicationState

fun NavGraphBuilder.scheduleListComposable(appState: ApplicationState) {
    composable<Route.ScheduleList> { entry ->
        ScheduleListScreen(appState = appState)
    }
}