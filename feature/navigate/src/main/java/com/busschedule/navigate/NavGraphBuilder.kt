package com.busschedule.navigate

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.busschedule.navigation.Route
import com.busschedule.schedulelist.ui.ScheduleListScreen
import com.busschedule.util.state.ApplicationState

fun NavGraphBuilder.scheduleListComposable(appState: ApplicationState) {
    composable<Route.ScheduleList> { entry ->
        ScheduleListScreen(appState = appState)
    }
}