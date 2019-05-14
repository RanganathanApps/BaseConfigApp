plugins {
    id("com.android.library")
    id("kotlin-android")
    id("kotlin-android-extensions")
}


android {
    compileSdkVersion(AppConfig.targetSdkVersion)
    flavorDimensions("default")
    defaultConfig {
        minSdkVersion(AppConfig.minSdkVersion)
        targetSdkVersion(AppConfig.targetSdkVersion)
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        maybeCreate("release").apply {
            isMinifyEnabled = false
        }
    }

    configurations {
    }



}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(Libs.Kotlin.kotlin_std)
    implementation(Libs.Kotlin.core_ktx)
    implementation(Libs.Support.appcompat)
    implementation(Libs.Support.constraint)

    implementation(Libs.picasso)
    implementation(Libs.runtimePermissions)
    implementation(Libs.material)
    implementation(Libs.coroutines)
    implementation(Libs.sdp)
    implementation(Libs.circleimageview)


    implementation(Libs.Test.espresso_core)
    implementation(Libs.Test.runner)


}
