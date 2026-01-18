plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    id("kotlin-kapt")
}
java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_11
    }
}
dependencies {
    implementation ("com.google.dagger:dagger:2.22")
    kapt ("com.google.dagger:dagger-compiler:2.22")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
}
