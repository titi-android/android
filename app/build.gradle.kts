plugins {
    id("busSchedule.android.application")
    id("busSchedule.android.hilt")
}

android {
    namespace = "com.example.busschedule.app"

    defaultConfig {
        applicationId = "com.example.busschedule"
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.feature.main)
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.ui.graphics)
//    testImplementation(libs.junit4)
}