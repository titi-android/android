plugins {
    id("busSchedule.android.application")
    id("busSchedule.android.hilt")
    id("busSchedule.android.compose")
}
kotlin {
    jvmToolchain(17)
}
android {
    namespace = "com.example.feature.main"

    defaultConfig {
        applicationId = "com.example.busschedule.feature.main"
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(libs.androidx.navigation.common.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(projects.core.util)
//    implementation(project(":core:util"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.ui.graphics)
//    testImplementation(libs.junit4)
}