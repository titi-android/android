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
    signingConfigs {
        create("release") {
            val keystorePropertiesFile = rootProject.file("./app/keystore/keystore.properties")
            val keystoreProperties = Properties().apply { load(keystorePropertiesFile.reader()) }
            storeFile = file(keystoreProperties["storePath"].toString())
            storePassword = keystoreProperties["storePassword"].toString()
            keyAlias = keystoreProperties["keyAlias"].toString()
            keyPassword = keystoreProperties["keyPassword"].toString()
        }
    }

    defaultConfig {
        applicationId = "com.schedule.notify.app"
        targetSdk = 35
        versionCode = 3
        versionName = "1.03"
        manifestPlaceholders
        multiDexEnabled = true
        signingConfig = signingConfigs.getByName("release")
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }

    buildTypes {
        debug {
            buildConfigField("String", "KAKAO_MAP_KEY", "\"" + localProperties.getProperty("KAKAO_MAP_KEY") + "\"")
        }
        release {
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
    implementation(projects.core.notification)
    implementation(projects.core.widget)
    implementation(projects.core.common)

    // work
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)

    // fcm
    implementation(libs.firebase.messaging.ktx)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit4)
    androidTestImplementation(libs.androidx.test.ext)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(libs.kakao.maps)
}