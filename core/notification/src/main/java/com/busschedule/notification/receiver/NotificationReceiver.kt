package com.busschedule.notification.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.busschedule.model.NotificationBuilder

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Notification 취소
        val notificationId = intent?.getIntExtra(NotificationBuilder.NOTIFYCODE_KEY, -1)!!
        Log.d("daeyoung", "알림 확인 버튼 클릭, notificationId: ${notificationId}")
        notificationManager.cancel(notificationId)
    }
}