plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("kotlin-kapt")
}

android {
    namespace = "es.jac.gymlog"
    compileSdk = 34

    defaultConfig {
        applicationId = "es.jac.gymlog"
        minSdk = 23
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
    }
}

dependencies {

    //ADDED BY JOEL ALACREU
    implementation ("androidx.fragment:fragment-ktx:1.6.1")//Cambiar de fragment
    implementation ("pl.droidsonroids.gif:android-gif-drawable:1.2.22")//Gif's
    implementation(platform("com.google.firebase:firebase-bom:32.7.1"))//AuthManager/Firestore
    implementation("com.google.firebase:firebase-auth")//AuthManager
    implementation("com.google.firebase:firebase-firestore-ktx")//Firestore
    implementation ("com.github.skydoves:colorpickerview:2.3.0") //Color
    implementation("androidx.activity:activity-ktx:1.8.2")//CallBackFlow
    implementation("com.squareup.retrofit2:retrofit:2.9.0")//Retrofit
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")//Retrofit
    implementation("androidx.room:room-ktx:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")

    //END

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.preference:preference:1.2.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}