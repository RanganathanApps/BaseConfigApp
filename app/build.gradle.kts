plugins {
    id("com.android.application")
    id("kotlin-android")
    id("kotlin-android-extensions")
}

android {
    compileSdkVersion(AppConfig.targetSdkVersion)
    flavorDimensions("default")
    defaultConfig {
        applicationId =AppConfig.applicaionId

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


}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    /*kotlin*/
    implementation(Libs.Kotlin.kotlin_std)
    /*androidx*/
    implementation(Libs.Kotlin.core_ktx)
    implementation(Libs.Support.appcompat)
    implementation(Libs.Support.constraint)
    implementation(Libs.Test.espresso_core)
    implementation(Libs.Test.runner)//androidTestImplementation

    implementation(project(":configlibrary"))

}


