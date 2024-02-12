@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hilt.android)
    id ("kotlin-kapt")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.ak.paging3"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.ak.paging3"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        buildConfigField("String", "BASE_URL", "${project.ext.get("BASE_URL")}")
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

    buildFeatures {
        viewBinding = true
        buildConfig = true

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {

    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)

    //Coroutine
    implementation (libs.kotlinx.coroutines.android)
    implementation (libs.lifecycle.viewmodel.ktx)

    // Retrofit
    implementation (libs.retrofit2.retrofit)
    implementation (libs.retrofit2.converter.gson)
    implementation (libs.okhttp3.logging.interceptor)

    //Dagger - Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Activity KTX for viewModels()
    implementation (libs.activity.ktx)

    implementation (libs.glide)
    implementation (libs.androidx.paging.runtime.ktx)
    implementation (libs.androidx.swiperefreshlayout)

}