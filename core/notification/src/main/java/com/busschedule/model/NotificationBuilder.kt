package com.busschedule.model

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.busschedule.notification.R
import com.busschedule.notification.receiver.NotificationReceiver

class NotificationBuilder(private val notificationManager: NotificationManager) {

    var notification: Notification? = null

    companion object {
        const val NOTIFYCODE_KEY = "notificationId"
        const val NOTIFYCODE = 1
    }

    private fun makeNotificationChannel(channelId: String, channelName: String) =
        NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT).apply {
            description = "My Channel One Description"
            enableLights(true)
            lightColor = Color.RED
            enableVibration(true)
            vibrationPattern = longArrayOf(100, 200, 100, 200)
        }

    private fun actionPendingIntent(context: Context): PendingIntent {
        val actionIntent =
            Intent(context, NotificationReceiver::class.java).putExtra(NOTIFYCODE_KEY, NOTIFYCODE)
        return PendingIntent.getBroadcast(context, 20, actionIntent, PendingIntent.FLAG_IMMUTABLE)
    }


    // 알림 채널, 객체 만들고 알림 매니저에 알림 채널을 등록한다.
    fun makeNotification(
        channelId: String,
        channelName: String,
        context: Context,
        title: String,
        body: String,
    ): Notification {
        if (notification != null) {
            return notification!!
        }
        val builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            notificationManager.createNotificationChannel(
                makeNotificationChannel(
                    channelId,
                    channelName
                )
            )
            NotificationCompat.Builder(context, channelId)
        } else {
            NotificationCompat.Builder(context)
        }
        // 알림 소리
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        notification = builder.run {
            setSmallIcon(androidx.core.R.drawable.notification_bg) // 아이콘 설정
            setContentTitle(title) // 제목
            setContentText(body) // 메시지 내용
            setWhen(System.currentTimeMillis()) // 알림 발생 시간
            setSmallIcon(R.drawable.ic_push_notify)
            addAction(
                NotificationCompat.Action.Builder(null, "확인", actionPendingIntent(context)).build()
            )
            setOngoing(true)
            setAutoCancel(true)
            setSound(soundUri) // 알림 소리
            build()
        }
        return notification!!
    }
}