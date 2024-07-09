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

rootProject.name = "dndcompanion"
include(":app")
include(":feature_auth")

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
include(":ui")
include(":feature_meta")
include(":feature_dnd")
include(":feature_main")
