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

package me.rei_m.hyakuninisshu.feature.support.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import me.rei_m.hyakuninisshu.feature.support.R
import me.rei_m.hyakuninisshu.feature.support.databinding.SupportFragmentBinding
import me.rei_m.hyakuninisshu.feature.support.di.SupportComponent
import javax.inject.Inject

class SupportFragment : Fragment() {
    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    private var _binding: SupportFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity() as SupportComponent.Injector)
            .supportComponent()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val versionName = getString(R.string.version, "3.1.4")

        _binding = SupportFragmentBinding.inflate(inflater, container, false)
        binding.textVersion.text = versionName

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        analyticsHelper.sendScreenView("Support", requireActivity())

        binding.buttonLicense.setOnClickListener {
            startActivity(Intent(requireActivity(), OssLicensesMenuActivity::class.java))
        }
        binding.buttonPrivacyPolicy.setOnClickListener {
            findNavController().navigate(me.rei_m.hyakuninisshu.feature.corecomponent.R.id.action_support_to_privacyPolicyDialog)
        }
        binding.buttonReview.setOnClickListener {
            val intent =
                Intent(
                    Intent.ACTION_VIEW,
                    getString(me.rei_m.hyakuninisshu.feature.corecomponent.R.string.app_url).toUri(),
                )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            requireActivity().startActivity(intent)
        }
        binding.buttonReader.setOnClickListener {
            val intent =
                Intent(
                    Intent.ACTION_VIEW,
                    getString(me.rei_m.hyakuninisshu.feature.corecomponent.R.string.reader_app_url).toUri(),
                )
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            requireActivity().startActivity(intent)
        }
    }
}
