package com.busschedule.navigate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.busschedule.login.LoginScreen
import com.busschedule.login.SignUpScreen
import com.busschedule.register.ui.RegisterBusScheduleScreen
import com.busschedule.register.ui.SelectBusScreen
import com.busschedule.register.ui.SelectRegionScreen
import com.busschedule.util.entity.navigation.Route
import com.example.connex.ui.domain.ApplicationState

fun NavGraphBuilder.loginGraph(appState: ApplicationState) {
    navigation<Route.LoginGraph>(startDestination = Route.LoginGraph.Login) {
        composable<Route.LoginGraph.Login> { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = appState.getNavController(),
                graph = Route.LoginGraph
            )
            LoginScreen(appState, loginViewModel = hiltViewModel(backStackEntry))
        }
        composable<Route.LoginGraph.Signup> { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = appState.getNavController(),
                graph = Route.LoginGraph
            )
            SignUpScreen(appState = appState, loginViewModel = hiltViewModel(backStackEntry))
        }

    }
}

fun NavGraphBuilder.registerBusScheduleGraph(appState: ApplicationState) {
    navigation<Route.RegisterGraph>(startDestination = Route.RegisterGraph.RegisterSchedule()) {
        composable<Route.RegisterGraph.RegisterSchedule> { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = appState.getNavController(),
                graph = Route.RegisterGraph
            )
            RegisterBusScheduleScreen(
                appState = appState,
                registerBusScheduleViewModel = hiltViewModel(backStackEntry)
            )
        }
        composable<Route.RegisterGraph.SelectRegion> { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = appState.getNavController(),
                graph = Route.RegisterGraph
            )
            SelectRegionScreen(
                appState = appState,
                registerBusScheduleViewModel = hiltViewModel(backStackEntry)
            )
        }
        composable<Route.RegisterGraph.SelectBusStop> { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = appState.getNavController(),
                graph = Route.RegisterGraph
            )
            SelectBusScreen(
                appState = appState,
                registerBusScheduleViewModel = hiltViewModel(backStackEntry),
                busStopInput =entry.arguments?.getString("busStop") ?: ""
            )
        }
    }
}

@Composable
fun rememberNavControllerBackEntry(
    entry: NavBackStackEntry,
    navController: NavController,
    graph: Route,
) = remember(entry) {
    navController.getBackStackEntry(graph)
}