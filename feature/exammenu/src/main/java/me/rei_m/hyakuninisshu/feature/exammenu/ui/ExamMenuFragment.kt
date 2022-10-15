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

/* ktlint-disable package-name */
package me.rei_m.hyakuninisshu.feature.exammenu.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import me.rei_m.hyakuninisshu.feature.exammenu.databinding.ExamMenuFragmentBinding
import me.rei_m.hyakuninisshu.feature.exammenu.di.ExamMenuComponent
import javax.inject.Inject

class ExamMenuFragment : Fragment() {

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    @Inject
    lateinit var viewModelFactory: ExamMenuViewModel.Factory

    private var _binding: ExamMenuFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<ExamMenuViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity() as ExamMenuComponent.Injector)
            .examMenuComponent()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ExamMenuFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyticsHelper.sendScreenView("ExamMenu", requireActivity())

        binding.buttonShowAllExamResults.setOnClickListener {
            val action = ExamMenuFragmentDirections.actionExamMenuToExamHistory()
            findNavController().navigate(action)
        }
        binding.buttonStartExam.setOnClickListener {
            analyticsHelper.logActionEvent(AnalyticsHelper.ActionEvent.START_EXAM)
            val action = ExamMenuFragmentDirections.actionExamMenuToExamStarter()
            findNavController().navigate(action)
        }
        binding.buttonStartTrainingFailedQuestion.setOnClickListener {
            analyticsHelper.logActionEvent(AnalyticsHelper.ActionEvent.START_TRAINING_FOR_EXAM)
            val action = ExamMenuFragmentDirections.actionExamMenuToExamPracticeTrainingStarter()
            findNavController().navigate(action)
        }
        binding.viewModel = viewModel
    }
}
