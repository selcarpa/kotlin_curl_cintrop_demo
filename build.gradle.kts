import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform") version "1.9.21"
}

group = "org.example"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}


kotlin {
    fun KotlinNativeTarget.config() {
        binaries {
            executable {
                entryPoint = "main"
            }
        }
    }

    linuxX64("linuxX64") {
        config()
        compilations["main"].cinterops {
            @Suppress("LocalVariableName") val libcurl by creating
        }
    }

    linuxArm64("linuxArm64") {
        config()
        compilations["main"].cinterops {
            @Suppress("LocalVariableName") val libcurl_arm64 by creating
        }
    }

    mingwX64("mingwX64"){
        config()
        compilations["main"].cinterops {
            @Suppress("LocalVariableName") val libcurl_mingwX64 by creating
        }
    }
}

tasks.register("cleanAndLinuxArm64"){
    group="curl_demo"
    dependsOn(tasks.findByName("clean"))
    dependsOn(tasks.findByName("linuxArm64MainBinaries"))
}

tasks.register("cleanAndMingwX64"){
    group="curl_demo"
    dependsOn(tasks.findByName("clean"))
    dependsOn(tasks.findByName("mingwX64MainBinaries"))
}

tasks.register("cleanAndLinuxX64"){
    group="curl_demo"
    dependsOn(tasks.findByName("clean"))
    dependsOn(tasks.findByName("linuxX64MainBinaries"))
}
