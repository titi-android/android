plugins {
    id("busSchedule.android.compose.library")
}

android {
    namespace = "com.busschedule.schedulelist"
}

dependencies {

    // hiltViewModel 사용
    implementation(libs.androidx.hilt.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(project(":core:designsystem"))
    implementation(project(":core:util"))
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}