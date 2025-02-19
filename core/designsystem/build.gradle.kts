import com.example.build_logic.configureCompose

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
}