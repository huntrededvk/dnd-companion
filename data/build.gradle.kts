plugins {
    id("local.library")
    id("local.hilt")
}

android {
    namespace = "com.khve.feature_dnd"
}

dependencies {
    implementation(libs.bundles.default)

    // Hilt
    implementation(libs.bundles.hilt)
    kapt(libs.hilt.android.compiler)

    // Retrofit
    implementation(libs.gson)
    implementation(libs.bundles.retrofit)
}