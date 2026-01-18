pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
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

rootProject.name = "Em"
include(":app")
include(":core")
include(":core:common_UI")
include(":feature:login")
include(":core:navigation")
include(":feature:menu")
include(":feature:login:presentation")
include(":core:dependencies")
include(":feature:login:domain")
include(":core:common")
include(":feature:login:data")
include(":feature:menu:presentation")
include(":feature:pageWithBar:presentation")
include(":feature:list:presentation")
include(":feature:list:domain")
include(":feature:list:data")
include(":feature:favorite")
include(":feature:main")
include(":feature:settings:presentation")
include(":feature:support:presentation")
include(":feature:account:presentation")
include(":core:network")
include(":core:favoriteDB")
