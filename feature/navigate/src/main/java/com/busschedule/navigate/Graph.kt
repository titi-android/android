package com.busschedule.navigate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.busschedule.login.login.ui.LoginScreen
import com.busschedule.login.signup.ui.SignUpScreen
import com.busschedule.login.splash.ui.SplashScreen
import com.busschedule.login.start.ui.AppStartScreen
import com.busschedule.model.BusStop
import com.busschedule.model.navtype.serializableType
import com.busschedule.navigation.LoginGraph
import com.busschedule.navigation.Route
import com.busschedule.register.ui.RegisterBusScheduleScreen
import com.busschedule.register.ui.SelectBusScreen
import com.busschedule.register.ui.SelectRegionScreen
import com.busschedule.setting.ui.SettingScreen
import com.busschedule.setting.ui.ask.ui.AskScreen
import com.busschedule.util.state.ApplicationState
import kotlin.reflect.typeOf

fun NavGraphBuilder.loginGraph(appState: ApplicationState) {
//    navigation<Route.LoginGraph>(startDestination = Route.LoginGraph.Login) {
    composable<LoginGraph.Splash> {
        SplashScreen(navigateToStart = {appState.navigateToStart()})
    }
    composable<LoginGraph.Start> {
        AppStartScreen(appState)
    }
    composable<LoginGraph.Login> {
        LoginScreen(appState)
    }
    composable<LoginGraph.Signup> {
        SignUpScreen(appState = appState)
    }
//    }
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
        composable<Route.RegisterGraph.SelectBusStop>(
            typeMap = mapOf(typeOf<BusStop?>() to serializableType<BusStop?>(isNullableAllowed = true))
        ) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = appState.getNavController(),
                graph = Route.RegisterGraph
            )
            SelectBusScreen(
                appState = appState,
                viewModel = hiltViewModel(backStackEntry),
                busStop = entry.toRoute<Route.RegisterGraph.SelectBusStop>().busStop
            )
        }
    }
}

fun NavGraphBuilder.settingGraph(appState: ApplicationState) {
    navigation<Route.SettingGraph>(startDestination = Route.SettingGraph.Setting) {
        composable<Route.SettingGraph.Setting> { entry ->
            SettingScreen(appState = appState)
        }
        composable<Route.SettingGraph.Ask> { entry ->
            AskScreen(appState)
        }

    }
}


@Composable
fun rememberNavControllerBackEntry(
    entry: NavBackStackEntry,
    navController: NavController,
    graph: com.busschedule.navigation.Route,
) = remember(entry) {
    navController.getBackStackEntry(graph)
}