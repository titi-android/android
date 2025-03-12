package com.busschedule.notification.receiver

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.busschedule.domain.repository.NotifyRepository
import com.busschedule.model.FCMMessage
import com.busschedule.model.NotificationBuilder
import com.busschedule.notification.constant.NotifyAction
import com.busschedule.notification.service.MyFirebaseMessagingService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationReceiver: BroadcastReceiver() {

    @Inject
    lateinit var repository: NotifyRepository

    private val receiverScope = CoroutineScope(Dispatchers.IO)
    override fun onReceive(context: Context, intent: Intent?) {
        receiverScope.launch {
            val scheduleId = intent?.getStringExtra("scheduleId") ?: ""
            val notifyScheduleEntity = repository.read(scheduleId)
            var nextBusStopIndex = notifyScheduleEntity.busStopIndex
            if (intent?.action == NotifyAction.CLICK_NEXT_BUTTON.value) {
                repository.updateBusStopIndex(scheduleId, ++nextBusStopIndex)
            }
            else if (intent?.action == NotifyAction.CLICK_PREVIOUS_BUTTON.value) {
                repository.updateBusStopIndex(scheduleId, --nextBusStopIndex)
            }

            val fcmMessage = FCMMessage(
                scheduleId = scheduleId,
                scheduleName = notifyScheduleEntity.scheduleName,
                busStopInfos = notifyScheduleEntity.busStopInfos[nextBusStopIndex]
            )

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            val notificationBuilder = NotificationBuilder(notificationManager)
            val notification = notificationBuilder.makeNotification(
                MyFirebaseMessagingService.CHANNEL_ID,
                MyFirebaseMessagingService.CHANNEL_NAME,
                context,
                scheduleId = scheduleId,
                maxBusStopSize = notifyScheduleEntity.busStopInfos.size,
                scheduleIndex = nextBusStopIndex,
                title = fcmMessage.getTitle(),
                body = fcmMessage.getContent()
            )

            // 알림 생성
            notificationManager.notify(NotificationBuilder.NOTIFYCODE, notification)
        }
    }
}