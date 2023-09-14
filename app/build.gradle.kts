plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id ("kotlin-kapt")
    id ("dagger.hilt.android.plugin")
    id ("io.realm.kotlin")
    id ("com.google.gms.google-services")

}

android {
    namespace = "com.example.diaryapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.diaryapplication"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

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
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18

    }
    kotlinOptions {
        jvmTarget = "18"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.6"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.7.2")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui:1.5.1")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation ("androidx.compose.material3:material3:1.1.1")
    implementation("androidx.compose.material3:material3-window-size-class:1.1.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Compose Navigation
    implementation ("androidx.navigation:navigation-compose:2.7.2")

    // Firebase
    implementation ("com.google.firebase:firebase-auth-ktx:22.1.1")
    implementation ("com.google.firebase:firebase-storage-ktx:20.2.1")

    // Room components
    implementation ("androidx.room:room-runtime:2.5.2")
    kapt ("androidx.room:room-compiler:2.5.2")
    implementation ("androidx.room:room-ktx:2.5.2")

    // Runtime Compose
    implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    // Splash API
    implementation ("androidx.core:core-splashscreen:1.0.1")

    // Mongo DB Realm
    implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
    implementation ("io.realm.kotlin:library-sync:1.11.1")

    // Dagger Hilt
    implementation ("com.google.dagger:hilt-android:2.44.2")
    kapt ("com.google.dagger:hilt-compiler:2.44.2")
    implementation ("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Coil
    implementation ("io.coil-kt:coil-compose:2.3.0")

    // Pager - Accompanist
    implementation ("com.google.accompanist:accompanist-pager:0.27.0")

    // Date-Time Picker
    implementation ("com.maxkeppeler.sheets-compose-dialogs:core:1.0.2")

    // CALENDAR
    implementation ("com.maxkeppeler.sheets-compose-dialogs:calendar:1.0.2")

    // CLOCK
    implementation ("com.maxkeppeler.sheets-compose-dialogs:clock:1.0.2")

    // Message Bar Compose
    implementation ("com.github.stevdza-san:MessageBarCompose:1.0.5")

    // One-Tap Compose
    implementation("com.github.stevdza-san:OneTapCompose:1.0.7")

    // Desugar JDK
    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:2.0.3")
//    coreLibraryDesugaring ("com.android.tools:desugar_jdk_libs:1.2.0")


    implementation("io.realm:realm-gradle-plugin:10.11.1")

    implementation ("com.google.android.gms:play-services-auth:20.7.0")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.2.3"))


    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics-ktx")



}