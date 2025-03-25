plugins {
    id("busSchedule.android.compose.library")
    alias(libs.plugins.ksp)
    id("busSchedule.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.busschedule.widget"
}

dependencies {
    implementation("androidx.glance:glance-appwidget:1.1.1")
    implementation( "androidx.glance:glance-material:1.1.1" )
    implementation( "androidx.glance:glance-material3:1.1.1" )

    implementation(projects.core.model)
    implementation(projects.core.domain)
    implementation(projects.core.common)
    implementation(projects.core.util)

    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.hilt.android)
    testImplementation(libs.hilt.testing)
    ksp(libs.hilt.compiler)
    kspTest(libs.hilt.compiler)
    implementation(libs.androidx.hilt.work)
    implementation(libs.androidx.hilt.common)
    ksp(libs.androidx.hilt.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext)

    androidTestImplementation(libs.androidx.espresso.core)
}