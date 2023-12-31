plugins {
    kotlin("multiplatform")
    kotlin("native.cocoapods")
    id("com.android.library")
    id("org.jetbrains.compose")
    id("app.cash.sqldelight")
}

sqldelight {
    databases {
        create("Database") {
            packageName.set("com.myapplication")
        }
    }
}


kotlin {
    // Versions
    val koinVersion = extra["koin.version"] as String
    val sqlDelightVersion = extra["sqldelight.version"] as String
    val kotlinxDateTime = extra["kotlinxdatetime.version"] as String
    val kmmViewModel = extra["kmmviewmodel.version"] as String

    androidTarget()

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        version = "1.0.0"
        summary = "Some description for the Shared Module"
        homepage = "Link to the Shared Module homepage"
        ios.deploymentTarget = "14.1"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = false
        }
    }

    sourceSets {
        all {
            languageSettings.optIn("kotlin.RequiresOptIn")
        }

        commonMain {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material)
                @OptIn(org.jetbrains.compose.ExperimentalComposeLibrary::class)
                implementation(compose.components.resources)
                implementation("app.cash.sqldelight:coroutines-extensions:$sqlDelightVersion")
                implementation("io.insert-koin:koin-core:$koinVersion")
                implementation("io.insert-koin:koin-compose:1.1.0")
                implementation("org.jetbrains.kotlinx:kotlinx-datetime:$kotlinxDateTime")
                implementation("com.rickclephas.kmm:kmm-viewmodel-core:$kmmViewModel")
                implementation("org.jetbrains.kotlinx:kotlinx-collections-immutable:0.3.5")
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation("io.insert-koin:koin-test:$koinVersion")
                implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3")
                implementation("io.mockk:mockk-common:1.12.5")
                implementation("app.cash.turbine:turbine:1.0.0")
            }
        }

        androidMain {
            dependencies {
                api("androidx.activity:activity-compose:1.8.0")
                api("androidx.appcompat:appcompat:1.6.1")
                api("androidx.core:core-ktx:1.12.0")
                implementation("app.cash.sqldelight:android-driver:$sqlDelightVersion")
                implementation("io.insert-koin:koin-androidx-compose:3.5.0")
                implementation("io.insert-koin:koin-android:3.5.0")

            }
        }

        androidNativeTest {
            dependencies {
                implementation(kotlin("test-junit"))
                implementation("io.mockk:mockk:1.13.8")
            }
        }

        iosMain {
            dependencies {
                implementation("app.cash.sqldelight:native-driver:$sqlDelightVersion")
            }
        }
    }
}

android {
    compileSdk = (findProperty("android.compileSdk") as String).toInt()
    namespace = "com.myapplication.common"

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    sourceSets["main"].res.srcDirs("src/androidMain/res")
    sourceSets["main"].resources.srcDirs("src/commonMain/resources")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String).toInt()
        targetSdk = (findProperty("android.targetSdk") as String).toInt()
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlin {
        jvmToolchain(11)
    }
}
