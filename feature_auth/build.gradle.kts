plugins {
    id("local.library")
    id("local.hilt")
    id("kotlin-parcelize")
}

android {
    namespace = "com.khve.feature_auth"
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

    // Gson
    implementation(libs.gson)

    // Modules
    implementation(projects.ui)

}