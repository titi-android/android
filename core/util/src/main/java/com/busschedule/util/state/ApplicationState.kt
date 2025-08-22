package com.busschedule.util.state

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import com.busschedule.model.BusRegister
import com.busschedule.model.BusStop
import com.busschedule.model.constant.TransitConst
import com.busschedule.navigation.LoginGraph
import com.busschedule.navigation.Route
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
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
            Route.RegisterSchedule(
                id = id,
                isExistTempSchedule = isExistTempSchedule,
                dayOfWeek = dayOfWeek
            )
        )
    }

    fun navigateToSelectRegion(id: Int) {
        navController.navigate(Route.SelectBus.SelectRegion(id))
    }

    fun navigateToSelectBusStop(busStop: BusStop) {
        navController.navigate(Route.SelectBus.SelectBusStop(busStop))
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
        navController.popBackStack<Route.RegisterSchedule>(inclusive = false)
    }

    fun popBackStackFromSubway(data: Map<String, String>) {
        navController.previousBackStackEntry
            ?.savedStateHandle.apply {
                data.forEach { key, value ->
                    this?.set(key, value)
                }
            }
        popBackStack()
    }

    fun popBackStackToScheduleRegisterFromBus(key: String = "busStopInfo", value: BusRegister) {
        val t = navController.getBackStackEntry<Route.RegisterSchedule>()
        val json = Json.encodeToString<BusRegister>(value)
        t.savedStateHandle.apply {
            set(TRANSIT_TYPE, TransitConst.BUS.name)
            set(key, json)
        }
        Log.i("daeyoung", "popBackStackToScheduleRegisterFromBus: ${t}")
        popBackStackRegister()
    }

    fun isBackFromSubway(): String {
        val transitType = navController.currentBackStackEntry?.savedStateHandle?.get<String>(TRANSIT_TYPE)
        navController.currentBackStackEntry?.savedStateHandle?.remove<String>(TRANSIT_TYPE)
        return transitType ?: ""
    }

    fun getSavedDataFromSubway() =
        listOf("regionName", "lineName", "stationName", "dir", "upDownDir").map {
            navController.currentBackStackEntry?.savedStateHandle?.getStateFlow(key = it, "")?.value
                ?: ""
        }

    fun getSavedDataFromBus(): BusRegister {
        val jsonString = navController.currentBackStackEntry?.savedStateHandle?.getStateFlow(
            "busStopInfo",
            ""
        )?.value
        return requireNotNull(Json.decodeFromString<BusRegister>(jsonString!!)) {
            "busStopInfo is null, RouteInfo 매핑 불가능"
        }
    }


    fun navigateEncodingUrl(prefixUrl: String, encodeUrl: String, param: String) {
        val encodedUrl = URLEncoder.encode(encodeUrl, StandardCharsets.UTF_8.toString())
        navController.navigate("$prefixUrl/$encodedUrl/$param")
    }

    companion object {
        private const val TRANSIT_TYPE = "transitType"
    }
}