/*
 * Copyright (c) 2020. Rei Matsushita.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package me.rei_m.hyakuninisshu.feature.trainingstarter.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.EventObserver
import me.rei_m.hyakuninisshu.feature.trainingstarter.databinding.TrainingStarterFragmentBinding
import me.rei_m.hyakuninisshu.feature.trainingstarter.di.TrainingStarterComponent
import me.rei_m.hyakuninisshu.state.question.model.Referer
import me.rei_m.hyakuninisshu.state.training.model.DisplayAnimationSpeedCondition
import me.rei_m.hyakuninisshu.state.training.model.DisplayStyleCondition
import me.rei_m.hyakuninisshu.state.training.model.InputSecondCondition
import javax.inject.Inject

class ExamPracticeTrainingStarterFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ExamPracticeTrainingStarterViewModel.Factory

    private var _binding: TrainingStarterFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ExamPracticeTrainingStarterViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity() as TrainingStarterComponent.Injector)
            .trainingStarterComponent()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = TrainingStarterFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding.viewModel = viewModel

        viewModel.onReadyEvent.observe(viewLifecycleOwner, EventObserver {
            val action = ExamPracticeTrainingStarterFragmentDirections
                .actionExamPracticeTrainingStarterToQuestion(
                    questionId = it,
                    kamiNoKuStyle = DisplayStyleCondition.KANJI,
                    shimoNoKuStyle = DisplayStyleCondition.KANA,
                    inputSecond = InputSecondCondition.NONE,
                    animationSpeed = DisplayAnimationSpeedCondition.NORMAL,
                    referer = Referer.Training
                )
            findNavController().navigate(action)
        })
    }
}
