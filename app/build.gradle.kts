import com.android.build.api.dsl.ComposeOptions
import org.gradle.kotlin.dsl.support.kotlinCompilerOptions

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)

    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)

}

android {
    namespace = "com.example.imagesearch"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.imagesearch"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures{
        viewBinding = true
        compose = true
    }

    composeOptions{
        kotlinCompilerExtensionVersion = "1.5.1"
    }


   
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    implementation("androidx.fragment:fragment-ktx:1.8.2")
    implementation("io.coil-kt:coil:2.4.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    implementation(libs.bundles.retrofit)

    //hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)

    //compose
    val composeBom = platform("androidx.compose:compose-bom:2022.10.00")
    implementation (composeBom)
    androidTestImplementation (composeBom)
    implementation("androidx.compose.material3:material3")
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
    implementation("androidx.activity:activity-compose:1.6.1")
    // Optional - Integration with ViewModels
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.4")
    //collectWithLifecycle사용
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.8.4")
    // Optional - Integration with LiveData
    implementation("androidx.compose.runtime:runtime-livedata")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")
}