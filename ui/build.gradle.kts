plugins {
    id("local.library")
}

android {
    namespace = "com.khve.ui"
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // YouTube Player
    implementation(libs.youtube.player.core)
}