import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

tasks.withType<KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("AndroidLibraryPlugin") {
            id = "busSchedule.android.library"
            implementationClass = "AndroidLibraryPlugin"
        }
        register("AndroidHiltPlugin") {
            id = "busSchedule.android.hilt"
            implementationClass = "AndroidHiltPlugin"
        }

        register("AndroidComposePlugin") {
            id = "busSchedule.android.compose"
            implementationClass = "AndroidComposePlugin"
        }
        register("AndroidFeaturePlugin") {
            id = "busSchedule.android.feature"
            implementationClass = "AndroidFeaturePlugin"
        }
        register("ApplicationPlugin") {
            id = "busSchedule.android.application"
            implementationClass = "ApplicationPlugin"
        }
    }
}