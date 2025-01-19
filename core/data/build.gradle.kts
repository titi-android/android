plugins {
    id("busSchedule.android.library")
    id("busSchedule.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.busschedule.data"
}

dependencies {

    implementation(libs.bundles.retrofit2)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}