package com.example.busschedule

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BusScheduleApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // FCM SDK 초기화
        FirebaseApp.initializeApp(this)
    }
}