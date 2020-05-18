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
package me.rei_m.hyakuninisshu.feature.exammenu.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.feature.corecomponent.di.FragmentScope
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.provideActivityViewModel
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import me.rei_m.hyakuninisshu.feature.exammenu.databinding.FragmentExamMenuBinding
import me.rei_m.hyakuninisshu.feature.exammenu.helper.Navigator
import javax.inject.Inject

class ExamMenuFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ExamMenuViewModel.Factory

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    private var _binding: FragmentExamMenuBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExamMenuBinding.inflate(inflater, container, false)
        binding.viewModel =
            provideActivityViewModel(ExamMenuViewModel::class.java, viewModelFactory)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyticsHelper.sendScreenView("Entrance - ExamMenu", requireActivity())

        binding.buttonShowAllExamResults.setOnClickListener {
            navigator.navigateToExamHistory()
        }
        binding.buttonStartExam.setOnClickListener {
            analyticsHelper.logActionEvent(AnalyticsHelper.ActionEvent.START_EXAM)
            navigator.navigateToExam()
        }
        binding.buttonStartTrainingFailedQuiz.setOnClickListener {
            analyticsHelper.logActionEvent(AnalyticsHelper.ActionEvent.START_TRAINING_FOR_EXAM)
            navigator.navigateToTrainingExam()
        }
    }

    @dagger.Module
    abstract class Module {
        @FragmentScope
        @ContributesAndroidInjector
        abstract fun contributeInjector(): ExamMenuFragment
    }

    companion object {

        const val TAG: String = "ExamMenuFragment"

        fun newInstance() = ExamMenuFragment()
    }
}
