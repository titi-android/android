package com.busschedule.util.remember

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.busschedule.util.state.ApplicationState
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberApplicationState(
    bottomBarState: MutableState<Boolean> = mutableStateOf(false),
    navController: NavHostController = rememberNavController(),
//    scaffoldState: ScaffoldState = rememberScaffoldState(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
//    uiController: SystemUiController = rememberSystemUiController(),
//    cameraPositionState: CameraPositionState = rememberCameraPositionState {},
): ApplicationState {
    val context = LocalContext.current
    val appState = remember(Unit) {
        ApplicationState(
            bottomBarState = bottomBarState,
            navController = navController,
            context = context,
//            snackbarHostState = snackbarHostState,
//            coroutineScope = coroutineScope,
        )
    }
    return appState
}