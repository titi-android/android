plugins {
    id("busSchedule.android.compose.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.busschedule.navigate"
}

dependencies {
    implementation(projects.core.designsystem)
    implementation(projects.feature.login)
    implementation(projects.core.navigation)
    implementation(projects.core.model)
    implementation(project(":core:util"))
    implementation(project(":feature:scheduleList"))
    implementation(project(":feature:register"))
    implementation(project(":feature:setting"))


    // hiltViewModel 사용
    implementation(libs.androidx.hilt.compose)
    implementation(libs.androidx.navigation.common.ktx)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.kotlinx.serialization.json)

}