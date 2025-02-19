package com.busschedule.notification.service

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import com.busschedule.model.FCMMessage
import com.busschedule.model.NotificationBuilder
import com.busschedule.notification.constant.FCMMsgData
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    private val TAG = "FirebaseService"

    override fun onNewToken(token: String) {
        Log.d(TAG, "new Token: $token")

//        // 토큰 값을 따로 저장해둔다.
//        val pref = this.getSharedPreferences("token", Context.MODE_PRIVATE)
//        val editor = pref.edit()
//        editor.putString("token", token).apply()
//        editor.commit()
//
//        Log.i("로그: ", "성공적으로 토큰을 저장함")
    }

    override fun onMessageReceived(message: RemoteMessage) {
        try {
            Log.d(TAG, "FCM DATA: ${message.data}")
            val notificationMessage = FCMMessage(
                scheduleName = message.data[FCMMsgData.SCHEDULENAME.value] ?: "",
                days = message.data[FCMMsgData.DAYS.value] ?: "",
                busStopName = message.data[FCMMsgData.BUSSTOPNAME.value] ?: "",
                firstBusName = message.data[FCMMsgData.FIRST_BUS_NAME.value] ?: "",
                firstArrPrevStCnt = message.data[FCMMsgData.FIRST_ARR_PRESTCNT.value] ?: "",
                firstArrTime = message.data[FCMMsgData.FIRST_ARR_TIME.value] ?: "",
                secondBusName = message.data[FCMMsgData.SECOND_BUS_NAME.value] ?: "",
                secondArrPrevStCnt = message.data[FCMMsgData.SECOND_ARR_PRESTCNT.value] ?: "",
                secondArrTime = message.data[FCMMsgData.SECOND_ARR_TIME.value] ?: "",
            )
            Log.d(TAG, "FCM DATA: ${notificationMessage}")
            sendNotification(title = notificationMessage.getTitle(), body = notificationMessage.getContent(), icon = 1)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun sendNotification(title: String, body: String, icon: Int) {
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationBuilder = NotificationBuilder(notificationManager)
        val notification = notificationBuilder.makeNotification(CHANNEL_ID, CHANNEL_NAME, this, title = title, body = body)

        // 알림 생성
        notificationManager.notify(NotificationBuilder.NOTIFYCODE, notification)
    }
    companion object {
        const val CHANNEL_ID = "channel_id1"
        const val CHANNEL_NAME = "channel_name"
    }
}