import com.vanniktech.maven.publish.SonatypeHost
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)

    id("com.vanniktech.maven.publish") version "0.28.0"
}
kotlin {
    explicitApi()
    androidTarget {
        publishAllLibraryVariants()
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    js(IR) {
        nodejs()
        browser()
        binaries.library()
    }
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    jvm()

    sourceSets {
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.materialIconsExtended)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(libs.jetbrains.material3.windowssizeclass)
        }
    }
}

android {
    namespace = "com.kappmaker.devicepreview"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    defaultConfig {
        minSdk = libs.versions.android.minSdk.get().toInt()
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

mavenPublishing {

    coordinates("com.kappmaker", "kmpdevicepreview", project.properties["devicePreviewVersion"] as String)
    pom {
        name = "KMPDevicePreview"
        description = "Kotlin & Compose Multiplatform library to simulate any device from any platform, supporting Android, iOS, JVM, JS, and WebAssembly."
        inceptionYear = "2024"
        url = "https://github.com/KAppMaker/KMPDevicePreview/"
        licenses {
            license {
                name.set("MIT")
                url.set("https://opensource.org/licenses/MIT")
            }
        }
        developers {
            developer {
                name.set("Mirzamehdi Karimov")
                email.set("mirzemehdi@gmail.com")
            }
        }
        scm {
            connection.set("https://github.com/KAppMaker/KMPDevicePreview.git")
            url.set("https://github.com/KAppMaker/KMPDevicePreview")
        }
        issueManagement {
            system.set("Github")
            url.set("https://github.com/KAppMaker/KMPDevicePreview/issues")
        }
    }

    publishToMavenCentral(SonatypeHost.CENTRAL_PORTAL)
    signAllPublications()
}
