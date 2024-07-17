@file:Suppress("UnstableApiUsage")

import de.fayard.refreshVersions.core.versionFor

plugins {
    id("android-lib")
    alias(libs.plugins.roborazzi)
    kotlin("plugin.serialization") version "1.8.0"
}

android {
    namespace = "com.louiscad.composeoclockplayground.shared"

    defaultConfig {
        minSdk = 26
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = versionFor(AndroidX.compose.compiler)
    }
    compileOptions {
        isCoreLibraryDesugaringEnabled = true
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
//    implementation(project(":app-watch"))
    api {
        libs.compose.oclock.core()
        Google.android.playServices.wearOS()

        platform(AndroidX.compose.bom)
        AndroidX.compose.ui()
        AndroidX.compose.ui.toolingPreview()
        AndroidX.compose.ui.text.googleFonts()

        AndroidX.core.ktx()
        AndroidX.lifecycle.runtime.ktx()
    }
    implementation("io.ktor:ktor-client-core:2.3.9")
    implementation("io.ktor:ktor-client-cio:2.3.9")
    implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.9")
    implementation("io.ktor:ktor-client-json:2.3.9")
    coreLibraryDesugaring(Android.tools.desugarJdkLibs)
    implementation {
        Splitties.systemservices()
        Splitties.appctx()
        Splitties.bitflags()
    }
    debugImplementation {
        AndroidX.compose.ui.tooling() //Important so previews can work.
        AndroidX.compose.ui.testManifest() // import for tests
    }
    testImplementation {
        Testing.junit4()
        Testing.robolectric()
        AndroidX.test.ext.junit.ktx()
        AndroidX.test.runner()
        AndroidX.compose.ui.testJunit4()
        libs.roborazzi()
        libs.roborazzi.compose()
        libs.roborazzi.rule()
    }
    androidTestImplementation {
        AndroidX.test.ext.junit.ktx()
        AndroidX.test.espresso.core()
    }
}
