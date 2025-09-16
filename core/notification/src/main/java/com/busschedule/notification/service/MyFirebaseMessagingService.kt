package com.busschedule.notification.service

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import com.busschedule.domain.repository.NotifyRepository
import com.busschedule.model.NotificationBuilder
import com.busschedule.model.NotifyMessage
import com.busschedule.notification.constant.FCMMsgData
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import javax.inject.Inject


@AndroidEntryPoint
class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = this::class.simpleName

    val serviceScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var repository: NotifyRepository

    override fun onNewToken(token: String) {
        serviceScope.launch {
            repository.saveFCMToken(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        try {
            Log.d(TAG, "FCM DATA: ${message.data}")

            val sectionsJson = message.data[FCMMsgData.SECTIONS.value] ?: "[]"
            val notifyMessages = Json.decodeFromString<List<NotifyMessage>>(sectionsJson)
            val scheduleId = message.data[FCMMsgData.SCHEDULE_ID.value] ?: ""
            val scheduleName = message.data[FCMMsgData.SCHEDULENAME.value] ?: ""
            serviceScope.launch {

                launch {
                    val isExist = async { repository.isExist(scheduleId) }.await()
                    if (isExist) {
                        repository.update(
                            scheduleId = scheduleId,
                            scheduleName = scheduleName,
                            notifyMessages = notifyMessages
                        )
                    } else {
                        repository.insert(
                            scheduleId = scheduleId,
                            scheduleName = scheduleName,
                            notifyMessages = notifyMessages
                        )
                    }

                }.join()

                val notifyScheduleEntity = repository.read(scheduleId)

                val curNotifyMsg =
                    notifyScheduleEntity.notifyMessages[notifyScheduleEntity.notifyIndex]

                sendNotification(
                    scheduleId = notifyScheduleEntity.scheduleId,
                    maxBusStopSize = notifyScheduleEntity.notifyMessages.size,
                    scheduleIndex = notifyScheduleEntity.notifyIndex,
                    title = curNotifyMsg.title,
                    body = curNotifyMsg.detail
                )

            }

        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "FCM 메시지 처리 중 오류 발생: ${e.message}")
        }
    }

    private fun sendNotification(
        scheduleId: String,
        maxBusStopSize: Int,
        scheduleIndex: Int,
        title: String,
        body: String,
    ) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilder = NotificationBuilder(notificationManager)
        val notification = notificationBuilder.makeNotification(
            CHANNEL_ID,
            CHANNEL_NAME,
            this,
            scheduleId = scheduleId,
            maxBusStopSize = maxBusStopSize,
            scheduleIndex = scheduleIndex,
            title = title,
            body = body
        )

        // 알림 생성
        notificationManager.notify(NotificationBuilder.NOTIFYCODE, notification)
    }

    companion object {
        const val CHANNEL_ID = "channel_id1"
        const val CHANNEL_NAME = "channel_name"
    }
}