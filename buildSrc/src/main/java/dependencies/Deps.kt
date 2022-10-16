/*
 * Copyright (c) 2020. Rei Matsushita
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

package dependencies

object Deps {
    object Kotlin {
        private const val version = "1.7.20"
        const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:$version"
        const val kotlinStdlibJdk7 = "org.jetbrains.kotlin:kotlin-stdlib-jdk7:$version"
        const val kotlinReflect = "org.jetbrains.kotlin:kotlin-reflect:$version"
    }

    object KotlinX {
        private const val coroutines_version = "1.6.4"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$coroutines_version"
        const val coroutinesAndroid ="org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutines_version"
    }

    object Google {
        object Gms {
            const val googleServices = "com.google.gms:google-services:4.3.14"
        }
        object Firebase {
            const val analytics = "com.google.firebase:firebase-analytics:21.2.0"
            const val crashlyticsGradle = "com.google.firebase:firebase-crashlytics-gradle:2.9.2"
            const val crashlytics = "com.google.firebase:firebase-crashlytics:18.3.0"
            const val ads = "com.google.firebase:firebase-ads:21.3.0"
        }
        object Android {
            object Gms {
                const val playServicesAds = "com.google.android.gms:play-services-ads:21.3.0"
                const val playServicesOssLicenses = "com.google.android.gms:play-services-oss-licenses:17.0.0"
                const val ossLicensesPlugin = "com.google.android.gms:oss-licenses-plugin:0.10.4"
            }
            object Material {
                const val material = "com.google.android.material:material:1.6.1"
            }
        }
    }

    object Android {
        object Tools {
            object Build {
                const val gradle = "com.android.tools.build:gradle:7.3.0"
            }
        }
    }

    object AndroidX {
        const val androidXAnnotation = "androidx.annotation:annotation:1.5.0"
        const val appcompat = "androidx.appcompat:appcompat:1.5.1"
        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.1.4"
        const val viewpager2 = "androidx.viewpager2:viewpager2:1.0.0"

        object Core {
            const val ktx = "androidx.core:core-ktx:1.9.0"
        }

        object Fragment {
            const val ktx = "androidx.fragment:fragment-ktx:1.5.3"
        }

        object Lifecycle {
            private const val version = "2.5.1"
            const val viewModelSavedState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:$version"
            const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:$version"
            const val liveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:$version"
            const val reactiveStreams = "androidx.lifecycle:lifecycle-reactivestreams:$version"
            const val commonJava8 = "androidx.lifecycle:lifecycle-common-java8:$version"
        }

        object Navigation {
            private const val version = "2.5.2"
            const val safeArgsGradlePlugin = "androidx.navigation:navigation-safe-args-gradle-plugin:$version"
            const val fragment = "androidx.navigation:navigation-fragment:$version"
            const val ui = "androidx.navigation:navigation-ui:$version"
            const val fragmentKtx = "androidx.navigation:navigation-fragment-ktx:$version"
            const val uiKtx = "androidx.navigation:navigation-ui-ktx:$version"
        }

        object Arch {
            const val coreTesting = "androidx.arch.core:core-testing:2.1.0"
        }

        object Test {
            private const val version = "1.4.0"
            const val core = "androidx.test:core:$version"
            const val runner = "androidx.test:runner:$version"
            const val rules = "androidx.test:rules:$version"
            const val junit = "androidx.test.ext:junit:1.1.2"

            object Espresso {
                private const val version = "3.4.0"
                const val core = "androidx.test.espresso:espresso-core:${version}"
                const val intents = "androidx.test.espresso:espresso-intents:${version}"
            }
        }
    }

    object RxJava2 {
        const val rxJava = "io.reactivex.rxjava2:rxjava:2.2.19"
        const val rxAndroid = "io.reactivex.rxjava2:rxandroid:2.1.1"
    }

    object Dagger {
        private const val version = "2.44"
        const val dagger = "com.google.dagger:dagger:$version"
        const val daggerAndroid = "com.google.dagger:dagger:$version"
        const val daggerAndroidSupport = "com.google.dagger:dagger:$version"
        const val daggerCompiler = "com.google.dagger:dagger-compiler:$version"
        const val daggerAndroidProcessor = "com.google.dagger:dagger-android-processor:$version"
    }

    object Orma {
        private const val version = "6.0.2"
        const val orma = "com.github.maskarade.android.orma:orma:$version"
        const val ormaProcessor = "com.github.maskarade.android.orma:orma-processor:$version"
    }

    object Glide {
        private const val version = "4.14.2"
        const val glide = "com.github.bumptech.glide:glide:$version"
        const val compiler = "com.github.bumptech.glide:compiler:$version"
    }

    object Moshi {
        private const val version = "1.14.0"
        const val moshi = "com.squareup.moshi:moshi:$version"
        const val kotlin = "com.squareup.moshi:moshi-kotlin:$version"
    }

    object AssertJ {
        const val core = "org.assertj:assertj-core:3.23.1"
    }

    object Robolectric {
        const val robolectric = "org.robolectric:robolectric:4.9"
    }

    object MockitoKotlin2 {
        const val mockitokotlin2 = "com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0"
    }

    const val androidRate = "com.github.hotchemi:android-rate:1.0.1"
    const val leakCanary = "com.squareup.leakcanary:leakcanary-android:2.9.1"
    const val timber = "com.jakewharton.timber:timber:4.7.1"

    const val ktlint = "com.github.shyiko:ktlint:0.38.1"
    const val gradleVersionsPlugin = "com.github.ben-manes:gradle-versions-plugin:0.29.0"
}
