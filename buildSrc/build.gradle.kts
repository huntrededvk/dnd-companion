plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(plugin(libs.plugins.jetbrainsKotlinAndroid))
    implementation(plugin(libs.plugins.kotlin.serialization))
    implementation(plugin(libs.plugins.kotlin.symbolProcessing))
    implementation(plugin(libs.plugins.androidApplication))
    implementation(plugin(libs.plugins.androidLibrary))
    implementation(plugin(libs.plugins.daggerHilt))
}

kotlin {
    jvmToolchain(17)
}

fun plugin(plugin: Provider<PluginDependency>) = plugin.map { "${it.pluginId}:${it.pluginId}.gradle.plugin:${it.version}" }