/*
 * Copyright (c) 2019. Rei Matsushita
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

package me.rei_m.hyakuninisshu.feature.support.helper

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope
import me.rei_m.hyakuninisshu.feature.support.R
import me.rei_m.hyakuninisshu.feature.support.ui.LicenceDialogFragment
import me.rei_m.hyakuninisshu.feature.support.ui.PrivacyPolicyDialogFragment
import javax.inject.Inject

@ActivityScope
class Navigator @Inject constructor(private val activity: AppCompatActivity) {
    fun navigateToAppStore() {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(activity.getString(R.string.app_url)))
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        activity.startActivity(intent)
    }

    fun openLicenceDialog() {
        if (activity.supportFragmentManager.findFragmentByTag(LicenceDialogFragment.TAG) == null) {
            LicenceDialogFragment.newInstance().show(activity.supportFragmentManager, LicenceDialogFragment.TAG)
        }
    }

    fun openPrivacyPolicy() {
        if (activity.supportFragmentManager.findFragmentByTag(PrivacyPolicyDialogFragment.TAG) == null) {
            PrivacyPolicyDialogFragment
                .newInstance().show(activity.supportFragmentManager, PrivacyPolicyDialogFragment.TAG)
        }
    }
}
