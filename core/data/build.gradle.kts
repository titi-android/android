plugins {
    id("busSchedule.android.library")
    id("busSchedule.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.busschedule.data"
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"http://3.34.0.32:8080\"")
    }
}

dependencies {
    implementation(projects.core.model)
    implementation(projects.core.domain)

    implementation(libs.bundles.retrofit2)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(project(":core:datastore"))
    implementation(project(":core:model"))
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}