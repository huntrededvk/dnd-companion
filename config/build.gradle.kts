plugins {
    id("local.library")
    id("local.hilt")
}

android {
    namespace = "com.khve.config"
}

dependencies {
    implementation(libs.bundles.default)

    // Firebase
    implementation(platform(libs.firebase.bom))
    implementation(libs.bundles.firebaseConfig)

    // Hilt
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)
}