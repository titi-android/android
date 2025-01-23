package com.example.busschedule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.busschedule.lock.service.LockServiceManager
import com.busschedule.navigate.RootNavHost
import com.busschedule.util.permission.OverlayPermission
import core.designsystem.theme.BusScheduleTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var lockServiceManager: LockServiceManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (OverlayPermission.alertPermissionCheck(this)) {
            OverlayPermission.onObtainingPermissionOverlayWindow(this)
        }

        startLockService()

        enableEdgeToEdge()
        setContent {
            BusScheduleTheme {
                RootNavHost()
            }
        }
    }
    private fun startLockService() {
        lockServiceManager.start()
    }
}