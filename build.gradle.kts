import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties
import org.jetbrains.dokka.gradle.DokkaTask

plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
    alias(libs.plugins.jetbrainsKotlinJvm) apply false
    alias(libs.plugins.dokka) apply false
    alias(libs.plugins.kotlinx.binary.validator)
    alias(libs.plugins.nexusPublish)
}


allprojects {
    group = "com.kappmaker.kmpdevicepreview"
    version = project.properties["devicePreviewVersion"] as String

    val gpgKeySecret = gradleLocalProperties(rootDir, providers).getProperty("gpgKeySecret")
    val gpgKeyPassword = gradleLocalProperties(rootDir, providers).getProperty("gpgKeyPassword")

    val excludedModules = listOf(":composeApp")
    if (project.path in excludedModules) return@allprojects

    apply(plugin = "org.jetbrains.dokka")
    apply(plugin = "maven-publish")
    apply(plugin = "signing")


    extensions.configure<PublishingExtension> {
        val javadocJar = tasks.register<Jar>("javadocJar") {
            dependsOn(tasks.getByName<DokkaTask>("dokkaHtml"))
            archiveClassifier.set("javadoc")
            from("${layout.buildDirectory}/dokka")
        }

        publications {
            withType<MavenPublication> {
                artifact(javadocJar)
                pom {
                    groupId = "com.kappmaker.kmpdevicepreview"
                    name.set("KMPDevicePreview")
                    description.set("Kotlin & Compose Multiplatform library to simulate any device from any platform, supporting Android, iOS, JVM, JS, and WebAssembly.")
                    licenses {
                        license {
                            name.set("MIT")
                            url.set("https://opensource.org/licenses/MIT")
                        }
                    }
                    url.set("kappmaker.com/KMPDevicePreview/")
                    issueManagement {
                        system.set("Github")
                        url.set("https://github.com/KAppMaker/KMPDevicePreview/issues")
                    }
                    scm {
                        connection.set("https://github.com/KAppMaker/KMPDevicePreview.git")
                        url.set("https://github.com/KAppMaker/KMPDevicePreview")
                    }
                    developers {
                        developer {
                            name.set("Mirzamehdi Karimov")
                            email.set("mirzemehdi@gmail.com")
                        }
                    }
                }
            }
        }
    }

    val publishing = extensions.getByType<PublishingExtension>()
    extensions.configure<SigningExtension> {
        useInMemoryPgpKeys(gpgKeySecret, gpgKeyPassword)
        sign(publishing.publications)
    }

    // TODO: remove after https://youtrack.jetbrains.com/issue/KT-46466 is fixed
    project.tasks.withType(AbstractPublishToMaven::class.java).configureEach {
        dependsOn(project.tasks.withType(Sign::class.java))
    }
}
nexusPublishing {
    repositories {
        sonatype {  //only for users registered in Sonatype after 24 Feb 2021
            nexusUrl.set(uri("https://s01.oss.sonatype.org/service/local/"))
            snapshotRepositoryUrl.set(uri("https://s01.oss.sonatype.org/content/repositories/snapshots/"))
            val sonatypeUsername =
                gradleLocalProperties(rootDir, providers).getProperty("sonatypeUsername")
            val sonatypePassword =
                gradleLocalProperties(rootDir, providers).getProperty("sonatypePassword")
            username = sonatypeUsername
            password = sonatypePassword
        }
    }
}

