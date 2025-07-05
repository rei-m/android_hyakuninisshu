/*
 * Copyright (c) 2025. Rei Matsushita
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kapt)
    alias(libs.plugins.google.services)
    alias(libs.plugins.firebase.crashlytics)
    alias(libs.plugins.oss.licenses)
    alias(libs.plugins.ktlint)
}

// Create a variable called keystorePropertiesFile, and initialize it to your
// keystore.properties file, in the rootProject folder.
val keystorePropertiesFile = rootProject.file("signingConfigs/keystore.properties")

// Initialize a new Properties() object called keystoreProperties.
val keystoreProperties = Properties()

// Load your keystore.properties file into the keystoreProperties object.
keystoreProperties.load(FileInputStream(keystorePropertiesFile))

android {
    namespace = "me.rei_m.hyakuninisshu"

    signingConfigs {
        create("releaseConfig") {
            keyAlias = keystoreProperties["keyAlias"] as String
            keyPassword = keystoreProperties["keyPassword"] as String
            storeFile = rootProject.file(keystoreProperties["storeFile"] as String)
            storePassword = keystoreProperties["storePassword"] as String
        }
    }

    compileSdk =
        libs.versions.compileSdk
            .get()
            .toInt()

    defaultConfig {
        applicationId = "me.rei_m.hyakuninisshu"
        minSdk =
            libs.versions.minSdk
                .get()
                .toInt()
        targetSdk =
            libs.versions.targetSdk
                .get()
                .toInt()
        versionCode =
            libs.versions.versionCode
                .get()
                .toInt()
        versionName = libs.versions.versionName.get()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        debug {
            isDebuggable = true
            applicationIdSuffix = ".debug"
            versionNameSuffix = "d"
            manifestPlaceholders["crashlyticsEnabled"] = false
        }
        release {
            signingConfig = signingConfigs.getByName("releaseConfig")
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
            manifestPlaceholders["crashlyticsEnabled"] = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        dataBinding = true
        buildConfig = true
    }
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
}

dependencies {
    implementation(project(":domain"))
    implementation(project(":infrastructure"))
    implementation(project(":feature:corecomponent"))
    implementation(project(":feature:splash"))
    implementation(project(":feature:question"))
    implementation(project(":feature:trainingmenu"))
    implementation(project(":feature:trainingstarter"))
    implementation(project(":feature:trainingresult"))
    implementation(project(":feature:exammenu"))
    implementation(project(":feature:examstarter"))
    implementation(project(":feature:examresult"))
    implementation(project(":feature:examhistory"))
    implementation(project(":feature:material"))
    implementation(project(":feature:materialedit"))
    implementation(project(":feature:support"))

    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
    ksp(libs.dagger.android.processor)

    implementation(libs.rate)

    debugImplementation(libs.leakcanary)
}

ktlint {
    android.set(true)
    outputColorName.set("RED")
}

project.android.applicationVariants.all {
    tasks.findByName("${name}OssLicensesTask")?.doLast {
        addOssLicense(project, "はんなり明朝", """
ライセンスはIPAフォントに準拠します。
https://moji.or.jp/ipafont/license/
""")
    }
}

fun addOssLicense(project: Project, libName: String, licenseContent: String) {
    val LINE_SEPARATOR = System.getProperty("line.separator").toByteArray(Charsets.UTF_8)

    project.android.applicationVariants.all {
        if (project.file("build/generated/third_party_licenses/$name").exists()) {
            val dependencyOutput = project.file("build/generated/third_party_licenses/$name")

            val resourceOutput = File(dependencyOutput, "res")
            val outputDir = File(resourceOutput, "raw")

            // ライセンスファイル
            val licensesFile = File(outputDir, "third_party_licenses")
            // ライセンスファイルへの書き込み前に現在の位置を保持
            val start = licensesFile.length()

            // ライセンスファイルへ書き込み
            licensesFile.appendText(licenseContent)
            licensesFile.appendBytes(LINE_SEPARATOR)

            // ライセンスメタデータファイルに書き込み
            val licensesMetadataFile = File(outputDir, "third_party_license_metadata")
            licensesMetadataFile.appendText("${start}:${licenseContent.toByteArray(Charsets.UTF_8).size} $libName")
            licensesMetadataFile.appendBytes(LINE_SEPARATOR)
        }
    }
}


// task addAppLicenseTask {
//    final String UTF_8 = "UTF-8"
//    final byte[] LINE_SEPARATOR = System.getProperty("line.separator").getBytes(UTF_8)
//
// //    mustRunAfter afterEvaluate {
// //        it.tasks.findByName('generateLicenses')
// //    }
// //    mustRunAfter 'releaseOssLicensesTask'
//
//    doLast {
//        def dependencyOutput = new File(project.buildDir, "generated/third_party_licenses")
//
//        def resourceOutput = new File(dependencyOutput, "/res")
//        def outputDir = new File(resourceOutput, "/raw")
//
//
//        def licensesFile = new File(outputDir, "third_party_licenses")
//        def licensesMetadataFile = new File(outputDir, "third_party_license_metadata")
//
//        def start = licensesFile.length()
//
//        def fontLicenseContent = """
// ライセンスはIPAフォントに準拠します。
// http://ipafont.ipa.go.jp/ipa_font_license_v1.html
// """
//        licensesFile << fontLicenseContent
//        licensesFile << (LINE_SEPARATOR)
//
//        licensesMetadataFile << ("${start}:${fontLicenseContent.getBytes("UTF-8").size()} はんなり明朝")
//        licensesMetadataFile << (LINE_SEPARATOR)
//    }
// }
// // preBuild前にライセンス情報を追加する
// tasks.findByPath(':app:preBuild').dependsOn addAppLicenseTask
