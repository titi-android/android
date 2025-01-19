plugins {
    id("busSchedule.android.application")
    id("busSchedule.android.compose")
    id("busSchedule.android.hilt")
}

android {
    namespace = "com.example.busschedule"

    defaultConfig {
        applicationId = "com.example.busschedule.app"
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
    implementation(projects.feature.navigate)
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.designsystem)
    implementation(projects.core.util)
    implementation(projects.core.datastore)
//    implementation(libs.androidx.core.ktx)
//    implementation(libs.androidx.lifecycle.runtime.ktx)
//    implementation(libs.androidx.ui.graphics)
//    testImplementation(libs.junit4)
}