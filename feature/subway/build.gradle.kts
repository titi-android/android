plugins {
    id("busSchedule.android.compose.library")
    id("busSchedule.android.hilt")
//    id("com.android.library")
//    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.busschedule.subway"
}

dependencies {

    implementation(projects.core.designsystem)
    implementation(projects.core.util)
    implementation(projects.core.model)
    implementation(projects.core.domain)

    // hiltViewModel 사용
    implementation(libs.androidx.hilt.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}