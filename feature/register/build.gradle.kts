plugins {
    id("busSchedule.android.compose.library")
    id("busSchedule.android.hilt")
}

android {
    namespace = "com.busschedule.register"
    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
}

dependencies {

    implementation(projects.core.domain)
    implementation(projects.core.designsystem)
    implementation(projects.core.util)
    implementation(projects.core.navigation)
    implementation(projects.core.model)
    implementation(projects.core.widget)

    // hiltViewModel 사용
    implementation(libs.androidx.hilt.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.work.runtime.ktx)
//    implementation(libs.ui.graphics)

    androidTestImplementation(libs.junit4)
//    implementation(libs.androidx.runner)
//    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.mockk.androidTest)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.kakao.maps)
    testImplementation(kotlin("test"))

}