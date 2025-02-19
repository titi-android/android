plugins {
    id("busSchedule.android.compose.library")
}
android {
    namespace = "com.busschedule.designsystem"
}

dependencies {
    implementation(libs.androidx.appcompat)

    // lottie
    implementation(libs.android.lottie.compose)
    // coil
    implementation(libs.coil.compose)
    implementation(libs.coil.gif)
}