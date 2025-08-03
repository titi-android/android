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
        maven { url = java.net.URI("https://devrepo.kakao.com/nexus/content/groups/public/") }
    }
}

//  Android build fail - Unable to make progress running work. 오류 해결
gradle.startParameter.excludedTaskNames.addAll(
    listOf(":build-logic:testClasses")
)

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
include(":core:notification")
include(":feature:setting")
include(":core:navigation")
include(":core:model")
include(":core:widget")
include(":core:common")
include(":feature:subway")
