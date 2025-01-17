package com.busschedule.navigate

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.busschedule.login.LoginScreen
import com.busschedule.util.constant.Constants
import com.example.connex.ui.domain.ApplicationState

fun NavGraphBuilder.loginComposable(applicationState: ApplicationState) {

    composable(
        route = Constants.LOGIN_ROUTE,
    ) { navBackStackEntry ->
        LoginScreen()
    }
}
