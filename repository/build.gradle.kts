import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
}

android {
    compileSdk = 32

    defaultConfig {
        minSdk = 23
        targetSdk = 32

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(project(Modules.model))
    implementation(project(Modules.core))
    //implementation(project(Modules.utils))

    // Retrofit
    implementation(Retrofit.retrofit)
    implementation(Retrofit.converter_gson)
    implementation(Retrofit.coroutinesAdapter)

    //Koin
    implementation(Koin.koin_core)
    implementation(Koin.koin_android)
    implementation(Koin.koin_android_compat)

    //Coroutines
    implementation(Coroutines.coroutines_core)
    implementation(Coroutines.coroutines_android)

    //Kotlin
    implementation(Kotlin.activity)
    implementation(Kotlin.core)

    //Design
    implementation(Design.swiperefreshlayout)
    implementation(Design.appcompat)
    implementation(Design.material)
    implementation(Design.constraintlayout)

    //Room
    implementation(Room.runtime)
    kapt(Room.room_compiler)
    implementation(Room.room_ktx)

    //Test
    testImplementation(TestImpl.junit)
    androidTestImplementation(TestImpl.junit_test)
    androidTestImplementation(TestImpl.espresso_core)
}
