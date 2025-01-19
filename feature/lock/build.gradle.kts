plugins {
    id("busSchedule.android.compose.library")
    id("busSchedule.android.hilt")
}

android {
    namespace = "com.busschedule.lock"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}