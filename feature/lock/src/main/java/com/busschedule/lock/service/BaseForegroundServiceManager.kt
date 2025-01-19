package com.busschedule.lock.service

import android.app.Service
import android.content.Context
import android.content.Intent
import android.util.Log
import com.busschedule.util.ext.isServiceRunning

abstract class BaseForegroundServiceManager<T : Service>(
    val context: Context,
    val targetClass: Class<T>,
) {
    fun start() = synchronized(this) {
        val intent = Intent(context, targetClass)

        if (!context.isServiceRunning(targetClass)) {
            Log.d("daeyoung", "BaseForegroundServiceManager start")
//            context.startForegroundService(intent)
            context.startService(intent)
        }
    }

    fun stop() = synchronized(this) {
        val intent = Intent(context, targetClass)

        if (context.isServiceRunning(targetClass)) {
            context.stopService(intent)
        }
    }
}