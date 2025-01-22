plugins {
    id("busSchedule.android.application")
    id("busSchedule.android.compose")
    id("busSchedule.android.hilt")
    alias(libs.plugins.google.service)
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
    implementation(projects.core.notification)
    implementation(project(":feature:lock"))
    implementation(libs.firebase.messaging.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

}