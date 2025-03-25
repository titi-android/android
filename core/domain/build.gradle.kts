plugins {
    id("busSchedule.android.library")
    id("busSchedule.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.busschedule.domain"
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.common)

    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit.serialization)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.espresso.core)
}