package com.busschedule.lock.service

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import com.busschedule.lock.receiver.LockReceiver
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LockService : Service() {

    @Inject
    lateinit var lockServiceManager: LockServiceManager

    override fun onBind(p0: Intent?): IBinder? {
        Log.d("daeyoung", "LockService onBind")
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        /* 푸쉬 알림
        createNotificationChannel()
        startForeground(SERVICE_ID, createNotificationBuilder())
         */
        Log.d("daeyoung", "LockService onStartCommand")
        startLockReceiver()
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        stopLockReceiver()
        lockServiceManager.stop()
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

//    private fun createNotificationChannel() {
//        val notificationChannel = SimpleNotificationBuilder.createChannel(
//            LOCK_CHANNEL,
//            getStringWithContext(R.string.app_name),
//            NotificationManager.IMPORTANCE_HIGH,
//            getStringWithContext(R.string.lock_screen_description)
//        )
//
//        val notificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        notificationManager.createNotificationChannel(notificationChannel)
//    }

//    private fun getStringWithContext(
//        @StringRes stringRes: Int
//    ): String {
//        return applicationContext.getString(stringRes)
//    }

//    private fun createNotificationBuilder(): Notification {
//        return SimpleNotificationBuilder.createBuilder(
//            context = this,
//            channelId = LOCK_CHANNEL,
//            title = getStringWithContext(R.string.app_name),
//            text = getStringWithContext(R.string.lock_screen_description),
//            icon = R.drawable.ic_launcher_foreground,
//        )
//    }

    private companion object {
        const val LOCK_CHANNEL = "LOCK_CHANNEL"
        const val SERVICE_ID: Int = 1
    }
}