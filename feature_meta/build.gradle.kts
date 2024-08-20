plugins {
    id("local.library")
    id("local.hilt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.khve.feature_meta"
}

dependencies {
    implementation(libs.bundles.default)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebase)

    // Coroutines
    implementation(libs.bundles.coroutines)
    implementation(libs.bundles.lifecycle)

    // Hilt
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)

    // UI
    implementation(libs.flexbox)

    // Gson
    implementation(libs.gson)

    // Glide
    implementation(libs.glide.v500rc01)

    // YouTube Player
    implementation(libs.youtube.player.core)

    // Modules
    implementation(projects.data)
    implementation(projects.featureAuth)
    implementation(projects.ui)

}