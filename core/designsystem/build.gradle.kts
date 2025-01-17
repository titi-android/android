import com.example.build_logic.configureCompose

plugins {
//    alias(libs.plugins.android.library)
    id("busSchedule.android.library")
    id("busSchedule.android.compose")
}
android {
    namespace = "core.designsystem"
}

dependencies {
    implementation(libs.androidx.appcompat)
}