package com.busschedule.navigate

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.busschedule.navigation.LoginGraph
import com.busschedule.util.remember.rememberApplicationState

@Composable
fun RootNavHost() {
    val appState = rememberApplicationState()
//    val navBackStackEntry by appState.getNavController().currentBackStackEntryAsState()

//    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//        NavHost(
//            navController = appState.getNavController(),
//            startDestination = Constants.LOGIN_GRAPH,
//            modifier = Modifier
//                .fillMaxSize().padding(innerPadding)
//        ) {
//            loginGraph(appState)
//            scheduleListComposable(appState)
//            registerBusScheduleGraph(appState)
//        }
//    }
    NavHost(
        navController = appState.getNavController(),
        startDestination = LoginGraph.Start,
        modifier = Modifier
            .fillMaxSize()
    ) {
        loginGraph(appState)
        scheduleListComposable(appState)
        registerBusScheduleGraph(appState)
        settingGraph(appState)
    }
}