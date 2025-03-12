plugins {
    id("busSchedule.android.library")
    id("busSchedule.android.hilt")
}

android {
    namespace = "com.busschedule.notification"
}

dependencies {

    implementation(libs.firebase.messaging.ktx)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(project(":core:model"))
    implementation(project(":core:util"))
    implementation(project(":core:domain"))
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}