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
import com.busschedule.model.BusStopInfo
import com.busschedule.model.BusStopInfoType
import com.busschedule.navigation.Route
import com.busschedule.register.ui.RegisterBusScheduleScreen
import com.busschedule.register.ui.SelectBusScreen
import com.busschedule.register.ui.SelectRegionScreen
import com.busschedule.setting.ui.AskScreen
import com.busschedule.setting.ui.ProfileEditScreen
import com.busschedule.setting.ui.SettingScreen
import com.busschedule.util.state.ApplicationState
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

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
        composable<Route.RegisterGraph.SelectBusStop>(
            typeMap = mapOf(typeOf<BusStopInfo>() to BusStopInfoType)
        ) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = appState.getNavController(),
                graph = Route.RegisterGraph
            )
            SelectBusScreen(
                appState = appState,
                registerBusScheduleViewModel = hiltViewModel(backStackEntry),
//                busStopInfo = entry.savedStateHandle.toRoute<Route.RegisterGraph.SelectBusStop>().busStopInfo
                busStopInfo = entry.savedStateHandle.get<String>("busStopInfo")?.let { str ->
                    Json.decodeFromString<BusStopInfo>(str)
                }

            )
        }
    }
}

fun NavGraphBuilder.settingGraph(appState: ApplicationState) {
    navigation<com.busschedule.navigation.Route.SettingGraph>(startDestination = com.busschedule.navigation.Route.SettingGraph.Setting) {
        composable<com.busschedule.navigation.Route.SettingGraph.Setting> { entry ->
            SettingScreen(appState = appState)
        }
        composable<com.busschedule.navigation.Route.SettingGraph.Ask> { entry ->
            AskScreen(appState)
        }
        composable<com.busschedule.navigation.Route.SettingGraph.EditProfile> { entry ->
            ProfileEditScreen(appState)
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