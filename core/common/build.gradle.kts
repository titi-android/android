plugins {
    id("busSchedule.android.library")
}

android {
    namespace = "com.busschedule.common"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.espresso.core)
}