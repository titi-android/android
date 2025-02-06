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
        maven("https://devrepo.kakao.com/nexus/repository/kakaomap-releases/")
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
include(":feature:register")
include(":core:data")
include(":core:domain")
include(":core:datastore")
include(":feature:lock")
include(":core:notification")
