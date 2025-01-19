import com.example.build_logic.configureCompose

plugins {
    id("busSchedule.android.compose.library")
}
android {
    namespace = "core.designsystem"
}

dependencies {
    implementation(libs.androidx.appcompat)
}