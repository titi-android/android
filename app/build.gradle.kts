import java.io.FileInputStream
import java.util.Properties

plugins {
    id("busSchedule.android.application")
    id("busSchedule.android.compose")
    id("busSchedule.android.hilt")
    alias(libs.plugins.google.service)
}

val localProperties = Properties().apply {
    load(FileInputStream(rootProject.file("local.properties")))
}

android {
    namespace = "com.example.busschedule"

    defaultConfig {
        applicationId = "com.example.busschedule.app"
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        manifestPlaceholders
        multiDexEnabled = true
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

    buildTypes {
        debug {
//            manifestPlaceholders["KAKAO_MAP_KEY"] = localProperties.getProperty("KAKAO_MAP_KEY")
            buildConfigField("String", "KAKAO_MAP_KEY", "\"" + localProperties.getProperty("KAKAO_MAP_KEY") + "\"")

        }
    }
}

dependencies {
    implementation(projects.feature.navigate)
    implementation(projects.core.data)
    implementation(projects.core.domain)
    implementation(projects.core.designsystem)
    implementation(projects.core.util)
    implementation(projects.core.datastore)
    implementation(projects.core.notification)
    implementation(projects.core.widget)
    implementation(projects.core.common)
    implementation(libs.firebase.messaging.ktx)

    // work
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.kakao.maps)
}