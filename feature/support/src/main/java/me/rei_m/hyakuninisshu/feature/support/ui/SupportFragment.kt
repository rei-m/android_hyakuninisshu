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
package me.rei_m.hyakuninisshu.feature.support.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.feature.corecomponent.di.FragmentScope
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import me.rei_m.hyakuninisshu.feature.support.BuildConfig
import me.rei_m.hyakuninisshu.feature.support.R
import me.rei_m.hyakuninisshu.feature.support.databinding.FragmentSupportBinding
import me.rei_m.hyakuninisshu.feature.support.helper.Navigator
import javax.inject.Inject

class SupportFragment : DaggerFragment() {

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val versionName = getString(R.string.version, BuildConfig.VERSION_NAME)

        val binding = FragmentSupportBinding.inflate(inflater, container, false)
        binding.textVersion.text = versionName
        binding.textLicense.setOnClickListener {
            navigator.openLicenceDialog()
        }
        binding.textPrivacyPolicy.setOnClickListener {
            navigator.openPrivacyPolicy()
        }
        binding.textReview.setOnClickListener {
            navigator.navigateToAppStore()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyticsHelper.sendScreenView("Entrance - Support", requireActivity())
    }

    @dagger.Module
    abstract class Module {
        @FragmentScope
        @ContributesAndroidInjector
        abstract fun contributeInjector(): SupportFragment
    }

    companion object {
        const val TAG: String = "SupportFragment"

        fun newInstance() = SupportFragment()
    }
}
