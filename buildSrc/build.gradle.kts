plugins{
    `java-gradle-plugin`
    `kotlin-dsl`
}

repositories{
    mavenCentral()
    jcenter()
    google()
}

buildscript {
    repositories {
        jcenter()
    }
    dependencies {
       // compileOnly("com.android.tools.build:gradle:3.4.0")
    }
}