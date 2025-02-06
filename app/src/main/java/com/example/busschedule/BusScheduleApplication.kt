package com.example.busschedule

import android.app.Application
import com.google.firebase.FirebaseApp
import com.kakao.vectormap.KakaoMapSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BusScheduleApplication : Application() {

//    var kakaoMapKey: String =

    override fun onCreate() {
        super.onCreate()
        // FCM SDK 초기화
        FirebaseApp.initializeApp(this)
        // Kakao SDK 초기화
        KakaoMapSdk.init(this, BuildConfig.KAKAO_MAP_KEY)
    }
}