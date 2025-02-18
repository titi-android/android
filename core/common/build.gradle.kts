plugins {
    id("busSchedule.android.library")
}

android {
    namespace = "com.busschedule.common"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}