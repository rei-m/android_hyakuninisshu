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

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.ktlint)
}

android {
    namespace = "me.rei_m.hyakuninisshu.infrastructure"

    compileSdk =
        libs.versions.compileSdk
            .get()
            .toInt()

    defaultConfig {
        minSdk =
            libs.versions.minSdk
                .get()
                .toInt()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    packaging {
        resources {
            pickFirsts.add("META-INF/atomicfu.kotlin_module")
        }
    }
}

dependencies {
    implementation(project(":domain"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)

    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
    ksp(libs.dagger.android.processor)

    implementation(libs.com.squareup.moshi)
    implementation(libs.com.squareup.moshi.kotlin)

    testImplementation(libs.bundles.test)
}
