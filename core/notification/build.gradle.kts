plugins {
    id("busSchedule.android.library")
    id("busSchedule.android.hilt")
}

android {
    namespace = "com.busschedule.notification"
}

dependencies {

    implementation(libs.firebase.messaging.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}