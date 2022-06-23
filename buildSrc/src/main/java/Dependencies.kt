import org.gradle.api.JavaVersion

object Config {
    const val application_id = "ru.profitsw2000.dictionarymvp"
    const val compile_sdk = 32
    const val min_sdk = 23
    const val target_sdk = 32
    val java_version = JavaVersion.VERSION_1_8
}

object Releases {
    const val version_code = 1
    const val version_name = "1.0"
}

object Modules {
    const val app = ":app"
    const val core = ":core"
    const val model = ":model"
    const val repository = ":repository"
    const val utils = ":utils"

    //Features
    //const val historyScreen = ":historyScreen"
}

object Versions {

    //Retrofit
    const val retrofit = "2.9.0"
    const val converterGson = "2.9.0"
    const val coroutinesAdapter = "0.9.2"

    //Koin
    const val koinCore = "3.2.0"
    const val koinAndroid = "3.2.0"
    const val koinAndroidCompat = "3.2.0"

    //Coroutines
    const val coroutinesCore = "1.6.2"
    const val coroutinesAndroid = "1.6.2"

    //Picasso
    const val picasso = "2.71828"

    //Glide
    const val glide = "4.11.0"
    const val glideCompiler = "4.11.0"

    //Coil
    const val coil = "0.11.0"

    //Room
    const val roomKtx = "2.4.2"
    const val runtime = "2.4.2"
    const val roomCompiler = "2.4.2"

    //Kotlin
    const val activity = "1.4.0"
    const val core = "1.8.0"

    //Design
    const val swiperefreshlayout = "1.1.0"
    const val appcompat = "1.4.2"
    const val material = "1.6.1"
    const val constraintlayout = "2.1.4"

    //Test
    const val jUnit = "4.13.2"
    const val jUnitTest = "1.1.3"
    const val espressoCore = "3.4.0"
}

object Retrofit {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converter_gson = "com.squareup.retrofit2:converter-gson:${Versions.converterGson}"
    const val coroutinesAdapter = "com.jakewharton.retrofit:retrofit2-kotlin-coroutines-adapter:${Versions.coroutinesAdapter}"
}

object Koin {
    const val koin_core = "io.insert-koin:koin-core:${Versions.koinCore}"
    const val koin_android = "io.insert-koin:koin-android:${Versions.koinAndroid}"
    const val koin_android_compat = "io.insert-koin:koin-android-compat:${Versions.koinAndroidCompat}"
}

object Coroutines {
    const val coroutines_core = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutinesCore}"
    const val coroutines_android = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutinesAndroid}"
}

object Picasso {
    const val picasso = "com.squareup.picasso:picasso:${Versions.picasso}"
}

object Glide {
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glideCompiler}"
}

object Coil {
    const val coil = "io.coil-kt:coil:${Versions.coil}"
}

object Room {
    const val runtime = "androidx.room:room-runtime:${Versions.runtime}"
    const val room_compiler = "androidx.room:room-compiler:${Versions.roomCompiler}"
    const val room_ktx = "androidx.room:room-ktx:${Versions.roomKtx}"
}

object Kotlin {
    const val activity = "androidx.activity:activity-ktx:${Versions.activity}"
    const val core = "androidx.core:core-ktx:${Versions.core}"
}

object Design {
    const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
}

object TestImpl {
    const val junit = "junit:junit:${Versions.jUnit}"
    const val junit_test = "androidx.test.ext:junit:${Versions.jUnitTest}"
    const val espresso_core = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
}