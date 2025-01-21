package com.busschedule.lock.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_SHORT_SERVICE
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.busschedule.lock.receiver.LockReceiver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LockService : Service() {

    @Inject
    lateinit var lockServiceManager: LockServiceManager

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val notification = createNotificationBuilder()
        // startForeground() 해야지 앱이 죽어도 서비스가 죽지 않음
        startForeground(SERVICE_ID, notification)
        Log.d("daeyoung", "LockService onStartCommand")
        startLockReceiver()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        stopLockReceiver()
        lockServiceManager.stop()
        Log.d("daeyoung", "LockService onDestroy")
        super.onDestroy()
    }

    private fun startLockReceiver() {
        val intentFilter = IntentFilter().apply {
            addAction(Intent.ACTION_SCREEN_ON)
            addAction(Intent.ACTION_SCREEN_OFF)
        }
        registerReceiver(LockReceiver, intentFilter)
    }

    private fun stopLockReceiver() {
        unregisterReceiver(LockReceiver)
    }

    private fun createNotificationChannel() {
        val notificationChannel = NotificationChannel(
            LOCK_CHANNEL_ID,
            LOCK_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_MIN
        )

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        notificationManager.createNotificationChannel(notificationChannel)
    }

    private fun createNotificationBuilder(): Notification {
        createNotificationChannel()
        val builder =
            NotificationCompat.Builder(this, LOCK_CHANNEL_ID).apply {
                setContentTitle("")
                setContentText("")
            }
        return builder.build()
    }

    private companion object {
        const val LOCK_CHANNEL_ID = "LOCK_CHANNEL_ID"
        const val LOCK_CHANNEL_NAME = "LOCK_CHANNEL_NAME"
        const val SERVICE_ID: Int = FOREGROUND_SERVICE_TYPE_SHORT_SERVICE
    }
}