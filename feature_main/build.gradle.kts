plugins {
    id("local.library")
    id("local.hilt")
}

android {
    namespace = "com.khve.feature_main"
}

dependencies {
    api(libs.bundles.default)
    api(libs.bundles.main)

    // Hilt
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)

    // Modules
    implementation(projects.featureAuth)
    implementation(projects.featureMeta)
    implementation(projects.ui)

    // Test
    testImplementation(libs.bundles.test)
    implementation(libs.kaspresso) { exclude(module = "protobuf-lite") }
    androidTestImplementation(libs.bundles.android.test)
    androidTestUtil(libs.androidx.orchestrator)
}