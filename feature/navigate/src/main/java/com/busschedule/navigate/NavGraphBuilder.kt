package com.busschedule.navigate

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.busschedule.navigation.Route
import com.busschedule.register.ui.RegisterScheduleScreen
import com.busschedule.schedulelist.ui.ScheduleListScreen
import com.busschedule.subway.ui.SubwayScreen
import com.busschedule.util.state.ApplicationState

fun NavGraphBuilder.scheduleListComposable(appState: ApplicationState) {
    composable<Route.ScheduleList> { entry ->
        ScheduleListScreen(appState = appState)
    }
}

fun NavGraphBuilder.registerScheduleComposable(appState: ApplicationState) {
    composable<Route.RegisterSchedule> { entry ->
        RegisterScheduleScreen(
            appState = appState,
            viewModel = hiltViewModel()
        )
    }
}

fun NavGraphBuilder.subwayComposable(appState: ApplicationState) {
    composable<Route.SelectSubway> { entry ->
        SubwayScreen(appState = appState, popBackStack = { appState.popBackStack() })
    }
}