plugins {
    id("busSchedule.android.library")
    id("busSchedule.android.compose")
}

android {
    namespace = "com.busschedule.register"
}

dependencies {

    // hiltViewModel 사용
    implementation(libs.androidx.hilt.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(project(":core:designsystem"))
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}