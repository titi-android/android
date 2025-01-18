enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
pluginManagement {
    includeBuild("build-logic")
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "BusSchedule"
include(":app")
include(":feature")
include(":core")
include(":core:designsystem")
include(":core:util")
include(":feature:login")
include(":feature:navigate")
include(":feature:scheduleList")
