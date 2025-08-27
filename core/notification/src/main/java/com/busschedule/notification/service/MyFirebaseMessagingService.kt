package com.busschedule.notification.service

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import com.busschedule.domain.repository.NotifyRepository
import com.busschedule.model.NotificationBuilder
import com.busschedule.model.RouteInfo
import com.busschedule.notification.constant.FCMMsgData
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
            Log.d("fcm", "FCM DATA: ${message.data}")

            val busStopInfosJson = message.data[FCMMsgData.SECTIONS.value] ?: "[]"
            val busStopInfos = Json.decodeFromString<List<RouteInfo>>(busStopInfosJson)
            val scheduleId = message.data[FCMMsgData.SCHEDULE_ID.value] ?: ""
            val scheduleName = message.data[FCMMsgData.SCHEDULENAME.value] ?: ""
            serviceScope.launch {
                /*
                launch {
                    val isExist = async { repository.isExist(scheduleId) }.await()
                    if (isExist) {
                        repository.update(
                            scheduleId = scheduleId,
                            scheduleName = scheduleName,
                            busStopInfos = busStopInfos
                        )
                    } else {
                        repository.insert(
                            scheduleId = scheduleId,
                            scheduleName = scheduleName,
                            busStopInfos = busStopInfos
                        )
                    }

                }.join()
                 */
                val notifyScheduleEntity = repository.read(scheduleId)

                /*

            val curBusStop = notifyScheduleEntity.busStopInfos[notifyScheduleEntity.busStopIndex]
            val notificationMessage = FCMMessage(
            scheduleId = notifyScheduleEntity.scheduleId,
            scheduleName = notifyScheduleEntity.scheduleName,
            busStopInfos = curBusStop
            )
            sendNotification(
            scheduleId = notificationMessage.scheduleId,
            maxBusStopSize = notifyScheduleEntity.busStopInfos.size,
            scheduleIndex = notifyScheduleEntity.busStopIndex,
            title = notificationMessage.getTitle(),
            body = notificationMessage.getContent()
            )
            */
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