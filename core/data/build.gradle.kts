import java.io.FileInputStream
import java.util.Properties

plugins {
    id("busSchedule.android.library")
    id("busSchedule.android.hilt")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
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
        buildConfigField(
            "String",
            "BASE_URL",
            "\"" + localProperties.getProperty("BASE_URL") + "\""
        )
        testInstrumentationRunner = "com.busschedule.data.HiltTestRunner"
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
    implementation(projects.core.model)
    implementation(projects.core.domain)

    implementation(libs.bundles.datastore)
    implementation(libs.bundles.retrofit2)
    implementation(libs.kotlinx.serialization.json)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.junit.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
//    testImplementation(libs.junit4)
    androidTestImplementation(libs.junit4)
    implementation(libs.androidx.runner)
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation("org.junit.jupiter:junit-jupiter-api:5.7.0") // JUnit 의존성 추가
    androidTestImplementation("org.junit.jupiter:junit-jupiter-engine:5.7.0") // JUnit 엔진
//    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.espresso.core)
}