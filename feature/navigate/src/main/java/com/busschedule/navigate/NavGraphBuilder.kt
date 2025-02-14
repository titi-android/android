package com.busschedule.navigate

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.busschedule.schedulelist.ui.ScheduleListScreen
import com.busschedule.navigation.Route
import com.busschedule.util.state.ApplicationState

fun NavGraphBuilder.scheduleListComposable(appState: ApplicationState) {
    composable<com.busschedule.navigation.Route.ScheduleList> { entry ->
        ScheduleListScreen(appState = appState)
    }
}