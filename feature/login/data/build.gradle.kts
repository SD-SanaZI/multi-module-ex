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
dependencies{
    implementation(project(":feature:login:domain"))
    implementation(project(":core:dependencies"))
    implementation(project(":core:common"))

    implementation ("com.google.dagger:dagger:2.22")
    kapt ("com.google.dagger:dagger-compiler:2.22")
}
