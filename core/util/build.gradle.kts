plugins {
    id("busSchedule.android.compose.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.busschedule.util"
}

dependencies {

    implementation(projects.core.navigation)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(project(":core:domain"))
    implementation(project(":core:model"))
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.kotlinx.serialization.json)
}