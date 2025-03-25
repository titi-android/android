plugins {
    id("busSchedule.android.compose.library")
    id("busSchedule.android.hilt")
}

android {
    namespace = "com.busschedule.setting"
}

dependencies {

    implementation(projects.core.designsystem)
    implementation(projects.core.navigation)
    implementation(projects.core.domain)
    implementation(projects.core.util)
    implementation(projects.core.common)

    // hiltViewModel 사용
    implementation(libs.androidx.hilt.compose)

    // fcm
    implementation(libs.firebase.messaging.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.espresso.core)
}