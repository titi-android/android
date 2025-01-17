import com.example.build_logic.configureCompose

plugins {
    id("busSchedule.android.library")
    id("busSchedule.android.compose")
}
android {
    namespace = "core.designsystem"
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
}


dependencies {
    implementation(libs.androidx.appcompat)
}