/*
 * Copyright (c) 2018. Rei Matsushita
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

/* ktlint-disable package-name */
package me.rei_m.hyakuninisshu.presentation.entrance

import android.content.Context
import me.rei_m.hyakuninisshu.BuildConfig
import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.presentation.helper.Navigator
import javax.inject.Inject

class SupportViewModel(
    val version: String,
    private val navigator: Navigator
) {

    fun onClickReview() {
        navigator.navigateToAppStore()
    }

    fun onClickLicense() {
        navigator.openLicenceDialog()
    }

    class Factory @Inject constructor(
        context: Context,
        private val navigator: Navigator
    ) {

        private val version = context.getString(R.string.version, BuildConfig.VERSION_NAME)

        fun create(): SupportViewModel = SupportViewModel(version, navigator)
    }
}
