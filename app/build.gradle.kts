plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.emsi.footville"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.emsi.footville"
        minSdk = 29 // Android 10
        targetSdk = 35
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    // Dépendances existantes
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Retrofit pour les appels API REST
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp pour les logs des requêtes
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // Glide pour le chargement des images
    implementation("com.github.bumptech.glide:glide:4.16.0")


    implementation("androidx.core:core-splashscreen:1.0.1")


    // Tests
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}