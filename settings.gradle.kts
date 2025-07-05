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

pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.google.android.gms.oss-licenses-plugin") {
                useModule("com.google.android.gms:oss-licenses-plugin:0.10.6")
            }
            if (requested.id.id == "androidx.navigation.safe-args-gradle-plugin") {
                useModule("androidx.navigation:navigation-safe-args-gradle-plugin:2.9.1")
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "android_hyakuninisshu"
include(":app")
include(":domain")
include(":infrastructure")
include(":state")
include(":feature:corecomponent")
include(":feature:splash")
include(":feature:question")
include(":feature:trainingmenu")
include(":feature:trainingstarter")
include(":feature:trainingresult")
include(":feature:exammenu")
include(":feature:examstarter")
include(":feature:examresult")
include(":feature:examhistory")
include(":feature:material")
include(":feature:materialedit")
include(":feature:support")
