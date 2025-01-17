package com.example.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.busschedule.util.constant.Constants

@Composable
fun RootNavhost(
    navBackStackEntry: NavBackStackEntry?,
//    appState: ApplicationState,
) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        val remenberNavController = rememberNavController()
        NavHost(
//            navController = appState.navController,
            navController = remenberNavController,
            startDestination = Constants,

            modifier = Modifier
                .fillMaxSize().padding(innerPadding)
        ) {

        }
    }
}