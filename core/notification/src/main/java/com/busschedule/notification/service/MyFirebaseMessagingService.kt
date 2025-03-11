package com.busschedule.notification.service

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import com.busschedule.data.local.datastore.TokenManager
import com.busschedule.data.local.room.dao.NotifyScheduleDao
import com.busschedule.data.local.room.model.NotifyScheduleEntity
import com.busschedule.model.BusStopInfo
import com.busschedule.model.FCMMessage
import com.busschedule.model.NotificationBuilder
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

    private val TAG = "FirebaseService"

    val serviceScope = CoroutineScope(Dispatchers.IO)

    @Inject
    lateinit var tokenManager: TokenManager

    @Inject
    lateinit var dao: NotifyScheduleDao

    override fun onNewToken(token: String) {
        serviceScope.launch {
            tokenManager.saveFCMToken(token)
        }
    }

    override fun onMessageReceived(message: RemoteMessage) {
        try {
            Log.d(TAG, "FCM DATA: ${message.data}")
            val busStopInfosJson = message.data[FCMMsgData.BUSSTOP_INFOS.value] ?: "[]"
            val busStopInfos = Json.decodeFromString<List<BusStopInfo>>(busStopInfosJson)
            val scheduleId = message.data[FCMMsgData.SCHEDULE_ID.value] ?: ""
            val scheduleName = message.data[FCMMsgData.SCHEDULENAME.value] ?: ""
            if (dao.isExist(scheduleId)) {
                dao.update( scheduleId = scheduleId, busStopInfos = busStopInfos )
            } else {
                dao.insert(
                    NotifyScheduleEntity(
                        scheduleId = scheduleId,
                        scheduleName = scheduleName,
                        busStopInfos = busStopInfos
                    )
                )
            }
            val notifyScheduleEntity = dao.read(scheduleId)
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
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, "FCM 메시지 처리 중 오류 발생: ${e.message}")
        }
    }

    private fun sendNotification(scheduleId: String, maxBusStopSize: Int, scheduleIndex: Int, title: String, body: String) {
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