plugins {
    id("busSchedule.android.compose.library")
    id("busSchedule.android.hilt")
}

android {
    namespace = "com.busschedule.login"
}

dependencies {

    implementation(projects.core.navigation)
    implementation(projects.core.designsystem)
    implementation(projects.core.common)
    implementation(projects.core.data)
    implementation(projects.core.model)
    implementation(project(":core:util"))
    implementation(project(":core:domain"))

    // fcm
    implementation(libs.firebase.messaging.ktx)

    // hiltViewModel 사용
    implementation(libs.androidx.hilt.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}