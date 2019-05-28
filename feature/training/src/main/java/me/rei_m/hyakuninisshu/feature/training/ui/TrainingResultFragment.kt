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

/* ktlint-disable package-name */
package me.rei_m.hyakuninisshu.feature.training.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.feature.corecomponent.di.FragmentScope
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.provideActivityViewModel
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import me.rei_m.hyakuninisshu.feature.training.databinding.FragmentTrainingResultBinding
import me.rei_m.hyakuninisshu.feature.training.helper.Navigator
import javax.inject.Inject

class TrainingResultFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: TrainingResultViewModel.Factory

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    private lateinit var binding: FragmentTrainingResultBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel = provideActivityViewModel(TrainingResultViewModel::class.java, viewModelFactory)

        binding = FragmentTrainingResultBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        // TODO: 暫定措置。。。
        viewModel.onCreateView()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyticsHelper.sendScreenView("TrainingResult", requireActivity())

        binding.buttonBack.setOnClickListener {
            navigator.back()
        }
    }

    @dagger.Module
    abstract class Module {
        @FragmentScope
        @ContributesAndroidInjector
        abstract fun contributeInjector(): TrainingResultFragment
    }

    companion object {

        const val TAG: String = "TrainingResultFragment"

        fun newInstance() = TrainingResultFragment()
    }
}
