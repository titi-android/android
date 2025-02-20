package com.busschedule.util.state

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import com.busschedule.model.BusStop
import com.busschedule.navigation.LoginGraph
import com.busschedule.navigation.Route
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Stable
class ApplicationState(
    private val bottomBarState: MutableState<Boolean>,
    private val navController: NavHostController,
    private val context: Context,
//    val scaffoldState: ScaffoldState,
    private val snackbarHostState: SnackbarHostState,
//    val cameraPositionState: CameraPositionState,
//    val systmeUiController: SystemUiController,
    private val coroutineScope: CoroutineScope,
) {
    var deepLink: Uri? = null

    fun getNavController() = navController

    fun changeBottomBarVisibility(visibility: Boolean) {
        bottomBarState.value = visibility
    }

    fun showSnackbar(message: String) {
        coroutineScope.launch {
//            Log.d("daeYoung", "showSnackbar(), snackbarHostState: ${snackbarHostState}")
            snackbarHostState.showSnackbar(message = message, duration = SnackbarDuration.Short)
        }
    }

    fun showToastMsg(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    fun popBackStack() {
        navController.popBackStack()
    }

    fun popBackStackInit(route: Route) {
//        val route = navController.currentBackStack.value
//        route.forEach {
//            Log.d("daeyoung", "nav: ${it.destination.route}")
//        }

//        navController.navigate(route) {
//            popUpTo(navController.graph.id) {
//                inclusive = true
//            }
//        }
    }

    fun navigate(uri: Uri) {
        navController.navigate(uri)
    }

    fun navigate(route: Route) {
        navController.navigate(route)
    }

    fun navigateToStart() {
        navController.navigate(LoginGraph.Start)
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
    fun navigateToRegister(id: Int? = null) {
        navController.navigate(Route.RegisterGraph.RegisterSchedule(id))
    }

    fun navigateToSelectRegion() {
        navController.navigate(Route.RegisterGraph.SelectRegion)
    }
    fun navigateToSelectBusStop(busStop: BusStop? = null) {
        if (busStop == null) {
            navController.navigate(Route.RegisterGraph.SelectBusStop())
            return
        }
        navController.navigate(Route.RegisterGraph.SelectBusStop(busStop))
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

    fun popBackStackRegister() {
        navController.popBackStack<Route.RegisterGraph.RegisterSchedule>(inclusive = false)
    }

    fun navigateEncodingUrl(prefixUrl: String, encodeUrl: String, param: String) {
        val encodedUrl = URLEncoder.encode(encodeUrl, StandardCharsets.UTF_8.toString())
        navController.navigate("$prefixUrl/$encodedUrl/$param")
    }
}