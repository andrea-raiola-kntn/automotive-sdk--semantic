import org.w3c.dom.Document
import javax.xml.parsers.DocumentBuilderFactory

val libraryVersion = rootProject.file("version.txt").readText().trim()

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
    id("com.google.devtools.ksp")
    id("org.jetbrains.dokka")
    id("maven-publish")
    jacoco
}

kotlin {
    jvmToolchain(17)
}

jacoco {
    toolVersion = "0.8.13"
}

detekt {
    config.setFrom("detekt.yml")
}

dokka {
    dokkaPublications.html {
        outputDirectory.set(rootDir.resolve("docs/${libraryVersion}"))
    }
}

publishing {
    publications {
        create<MavenPublication>("coreRelease") {
            afterEvaluate {
                from(components["coreRelease"])
            }
            groupId = "com.kineton.automotive"
            artifactId = "sdk"
            version = libraryVersion
        }

        create<MavenPublication>("coreDebug") {
            afterEvaluate {
                from(components["coreDebug"])
            }
            groupId = "com.kineton.automotive"
            artifactId = "sdk"
            version = libraryVersion + "-debug"
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/lorenzopaolo-cocchinone/automotive-sdk")
            credentials {
                username = System.getenv("USERNAME_GITHUB")
                password = System.getenv("TOKEN_GITHUB")
            }
        }
    }
}

android {
    namespace = "com.kineton.automotive.sdk"
    flavorDimensions += "oem"
    compileSdk = 36

    defaultConfig {
        minSdk = 28
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    publishing {
        singleVariant("coreRelease") {
            withSourcesJar()
            withJavadocJar()
        }

        singleVariant("coreDebug") {
            withSourcesJar()
            withJavadocJar()
        }
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("Boolean", "NETWORK_LOGGING", "false")
        }

        debug {
            enableAndroidTestCoverage = true
            buildConfigField("Boolean", "NETWORK_LOGGING", "true")
        }
    }

    productFlavors {
        create("core") {
            dimension = "oem"
        }
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

val jacocoExclusions = listOf(
    "**/R.class", "**/R$*.class", "**/BuildConfig.*", "**/Manifest*.*",
    "**/AutomotiveSDKDatabase.*", "**/AutomotiveSDKDatabase_Impl**.*",
    "**/com/kineton/automotive/sdk/initializers/**",
    "**/com/kineton/automotive/sdk/daos/**"
)

tasks.withType<Test> {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

tasks.register<JacocoReport>("jacocoCoverage") {
    group = "Reporting"
    description = "Execute Unit Test and Instrumentation Test, generate and combine Jacoco coverage report"

    reports {
        xml.required.set(true)
        html.required.set(true)
    }

    sourceDirectories.setFrom(layout.projectDirectory.dir("src/main/java"))
    classDirectories.setFrom(
        files(fileTree(layout.buildDirectory.dir("tmp/kotlin-classes/coreDebug")) {
            exclude(jacocoExclusions)
        })
    )
    executionData.setFrom(fileTree(layout.buildDirectory) {
        include("**/*.ec", "**/*.exec")
    })

    doLast {
        val reportFile = layout.buildDirectory.file("reports/jacoco/jacocoCoverage/jacocoCoverage.xml").get().asFile
        val htmlDir = layout.buildDirectory.dir("reports/jacoco/jacocoCoverage/html").get().asFile

        if (!reportFile.exists()) {
            println("Coverage report not found: ${reportFile.path}")
            return@doLast
        }

        val doc: Document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(reportFile.apply {
            writeText(readText().replace(Regex("""(?i)<!DOCTYPE[^>]+>"""), ""))
        })
        val counters = doc.getElementsByTagName("counter")

        val (covered, missed) = (0 until counters.length).fold(0 to 0) { acc, i ->
            val attr = counters.item(i).attributes
            if (attr.getNamedItem("type").nodeValue == "LINE") {
                val c = attr.getNamedItem("covered").nodeValue.toInt()
                val m = attr.getNamedItem("missed").nodeValue.toInt()
                (acc.first + c) to (acc.second + m)
            } else acc
        }

        val total = covered + missed
        val percent = if (total > 0) (covered * 100.0 / total) else 0.0
        println("✔ Code coverage: %.2f%% (%d/%d lines covered)".format(percent, covered, total))
        println("📄 HTML report available at: file://${htmlDir.absolutePath}/index.html")
    }
}

dependencies {
    // App Startup
    implementation(libs.androidx.startup.runtime)

    // Room Database
    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    // MMKV
    implementation(libs.mmkv)

    // Dagger for Dependency injection
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)

    // Okhttp3
    implementation(libs.okhttp)

    // Jackson
    implementation(libs.jackson.module.kotlin)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.converter.jackson)

    // Test
    testImplementation(libs.junit)
    testImplementation(libs.mockito.core)
    testImplementation(libs.robolectric)

    // Android Test
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.runner)
}
