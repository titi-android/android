package com.example.connex.ui.domain

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import com.busschedule.util.constant.Constants
import com.busschedule.util.entity.navigation.Route
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
    private var currentRoute: String? = Constants.HOME_GRAPH
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

    fun popBackStackHome() {
        navController.navigate(Constants.HOME_GRAPH) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
        }
    }

    fun navigate(route: String) {
        navController.navigate(route)
        currentRoute = route
    }

    fun navigate(uri: Uri) {
        navController.navigate(uri)
    }

    fun navigate(route: Route) {
        navController.navigate(route)
    }

    fun navigateEncodingUrl(prefixUrl: String, encodeUrl: String, param: String) {
        val encodedUrl = URLEncoder.encode(encodeUrl, StandardCharsets.UTF_8.toString())
        navController.navigate("$prefixUrl/$encodedUrl/$param")
    }

    fun navigatePopBackStack(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.id) {
                inclusive = true
            }
            currentRoute = route
        }
    }
}