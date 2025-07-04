plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("maven-publish")
    id("io.gitlab.arturbosch.detekt") version "1.23.8"
}

kotlin {
    jvmToolchain(17)
}

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                groupId = "com.kineton.automotive"
                artifactId = "sdk"
                version = rootProject.file("version.txt").readText().trim()
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
}


android {
    namespace = "com.kineton.automotive.sdk"
    compileSdk = 36

    defaultConfig {
        minSdk = 34
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
}

dependencies {
    // Core API to access Android API
    implementation(libs.androidx.core.ktx)
    // Test API
    testImplementation(libs.junit)
    // Android Test API
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
