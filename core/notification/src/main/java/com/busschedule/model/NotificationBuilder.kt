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
import com.busschedule.notification.constant.NotifyAction
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

    private fun actionPendingIntent(
        context: Context,
        actionFlag: String,
        scheduleId: String,
    ): PendingIntent {
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            action = actionFlag
            putExtra("scheduleId", scheduleId)
        }

        return PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_MUTABLE
        )
    }


    // 알림 채널, 객체 만들고 알림 매니저에 알림 채널을 등록한다.
    fun makeNotification(
        channelId: String,
        channelName: String,
        context: Context,
        scheduleId: String,
        maxBusStopSize: Int,
        scheduleIndex: Int,
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
        val actionCount = when (scheduleIndex) {
            0 -> listOf(NotifyAction.CLICK_NEXT_BUTTON)
            maxBusStopSize - 1 -> listOf(NotifyAction.CLICK_PREVIOUS_BUTTON)
            else -> {
                listOf(NotifyAction.CLICK_PREVIOUS_BUTTON, NotifyAction.CLICK_NEXT_BUTTON)
            }
        }

        notification = builder.run {
            setOngoing(true)
            setSmallIcon(com.busschedule.notification.R.drawable.ic_push_notify) // 아이콘 설정
            setColor(context.getColor(com.busschedule.notification.R.color.notify_background))
            setContentTitle(title) // 제목
            setContentText(body) // 메시지 내용
            setWhen(System.currentTimeMillis()) // 알림 발생 시간
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC) // 잠금 화면에서 알림 보임
            actionCount.forEach {
                addAction(
                    NotificationCompat.Action.Builder(
                        null, it.text,
                        actionPendingIntent(
                            context = context,
                            actionFlag = it.value,
                            scheduleId = scheduleId
                        )
                    ).build()
                )
            }

//            setAutoCancel(true)
            setSound(soundUri) // 알림 소리
            build()
        }
        return notification!!
    }
}