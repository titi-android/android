plugins {
    id("busSchedule.android.compose.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.busschedule.model"
}

dependencies {
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.navigation.common.android)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.espresso.core)
}