import java.io.FileInputStream
import java.util.Properties

plugins {
    id("busSchedule.android.library")
    id("busSchedule.android.hilt")
    alias(libs.plugins.kotlin.serialization)
}

val localProperties = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
}

android {
    namespace = "com.busschedule.data"
    buildFeatures {
        buildConfig = true
    }
    defaultConfig {
        buildConfigField("String", "BASE_URL", "\"" + localProperties.getProperty("BASE_URL") + "\"")
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