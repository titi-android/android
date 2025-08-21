package com.busschedule.util.state

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import com.busschedule.model.BusStop
import com.busschedule.navigation.LoginGraph
import com.busschedule.navigation.Route
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Stable
class ApplicationState(
    private val bottomBarState: MutableState<Boolean>,
    private val navController: NavHostController,
    private val context: Context,
//    val scaffoldState: ScaffoldState,
//    private val snackbarHostState: SnackbarHostState,
//    val systmeUiController: SystemUiController,
) {
    fun getNavController() = navController

    fun showToastMsg(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun popBackStack() {
        navController.popBackStack()
    }


    fun navigate(route: Route) {
        navController.navigate(route)
    }

    fun navigateToStart() {
        navController.navigate(route = LoginGraph.Start) {
            popUpTo<LoginGraph.Splash> { inclusive = true }
        }
    }

    fun navigateToLogin() {
        navController.navigate(LoginGraph.Login)
    }

    fun navigateToSignUp() {
        navController.navigate(LoginGraph.Signup)
    }

    fun navigateToScheduleList() {
        navController.navigate(Route.ScheduleList)
    }

    fun navigateToRegister(
        id: Int? = null,
        isExistTempSchedule: Boolean = false,
        dayOfWeek: String = "월",
    ) {
        navController.navigate(
            Route.RegisterGraph.RegisterSchedule(
                id = id,
                isExistTempSchedule = isExistTempSchedule,
                dayOfWeek = dayOfWeek
            )
        )
    }

    fun navigateToSelectRegion(id: Int) {
        navController.navigate(Route.RegisterGraph.SelectRegion(id))
    }

    fun navigateToSelectBusStop(busStop: BusStop) {
        navController.navigate(Route.RegisterGraph.SelectBusStop(busStop))
    }

    fun navigateToSelectSubway() {
        navController.navigate(Route.SelectSubway)
    }

    fun navigateToSetting() {
        navController.navigate(Route.SettingGraph.Setting)
    }

    fun navigateToAsk() {
        navController.navigate(Route.SettingGraph.Ask)
    }

    fun navigateToEditProfile() {
        navController.navigate(Route.SettingGraph.EditProfile)
    }

    fun popBackStackStart() {
        navController.popBackStack<LoginGraph.Start>(inclusive = false)
    }

    fun popBackStackRegister() {
        navController.popBackStack<Route.RegisterGraph.RegisterSchedule>(inclusive = false)
    }

    fun getPreviousBackStackEntry() = navController.previousBackStackEntry
        ?.savedStateHandle

    fun popBackStackFromSubway(data: Map<String, String>) {
        navController.previousBackStackEntry
            ?.savedStateHandle.apply {
                data.forEach { key, value ->
                    this?.set(key, value)
                }
            }
        popBackStack()
    }


    fun navigateEncodingUrl(prefixUrl: String, encodeUrl: String, param: String) {
        val encodedUrl = URLEncoder.encode(encodeUrl, StandardCharsets.UTF_8.toString())
        navController.navigate("$prefixUrl/$encodedUrl/$param")
    }
}