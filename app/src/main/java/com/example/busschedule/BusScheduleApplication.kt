package com.example.busschedule

import android.app.Application
import android.util.Log
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.google.firebase.FirebaseApp
import com.kakao.sdk.common.util.Utility
import com.kakao.vectormap.KakaoMapSdk
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class BusScheduleApplication : Application(), Configuration.Provider {

    @Inject
    lateinit var workFactory: HiltWorkerFactory
    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(workFactory)
            .build()

    override fun onCreate() {
        super.onCreate()

        var keyHash = Utility.getKeyHash(this)
        Log.d("daeyoung", "keyHash: $keyHash")

        // FCM SDK 초기화
        FirebaseApp.initializeApp(this)
        // Kakao SDK 초기화
        KakaoMapSdk.init(this, BuildConfig.KAKAO_MAP_KEY)

    }
}