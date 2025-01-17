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
import com.busschedule.util.constant.Constants
import com.example.connex.ui.domain.ApplicationState

fun NavGraphBuilder.loginGraph(appState: ApplicationState) {
    navigation(startDestination = Constants.LOGIN_ROUTE, route = Constants.LOGIN_GRAPH) {
        composable(route = Constants.LOGIN_ROUTE) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = appState.getNavController(),
                graph = Constants.LOGIN_GRAPH
            )
            LoginScreen(appState, loginViewModel = hiltViewModel(backStackEntry))
        }
        composable(route = Constants.SIGNUP_ROUTE) { entry ->
            val backStackEntry = rememberNavControllerBackEntry(
                entry = entry,
                navController = appState.getNavController(),
                graph = Constants.LOGIN_GRAPH
            )
            SignUpScreen(appState = appState, loginViewModel = hiltViewModel(backStackEntry))
        }

    }
}

@Composable
fun rememberNavControllerBackEntry(
    entry: NavBackStackEntry,
    navController: NavController,
    graph: String,
) = remember(entry) {
    navController.getBackStackEntry(graph)
}