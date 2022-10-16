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

package me.rei_m.hyakuninisshu.feature.question.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import me.rei_m.hyakuninisshu.feature.question.databinding.AnswerFragmentBinding
import me.rei_m.hyakuninisshu.feature.question.di.QuestionComponent
import me.rei_m.hyakuninisshu.state.question.model.Referer
import javax.inject.Inject

class AnswerFragment : Fragment() {
    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    private val args: AnswerFragmentArgs by navArgs()

    private var _binding: AnswerFragmentBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity() as QuestionComponent.Injector)
            .questionComponent()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = AnswerFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyticsHelper.sendScreenView("Answer", requireActivity())

        binding.material = args.correctKaruta
        binding.existNextQuestion = args.nextQuestionId != null
        binding.buttonGoToNext.setOnClickListener {
            args.nextQuestionId?.let {
                val action = AnswerFragmentDirections.actionAnswerToQuestion(
                    questionId = it,
                    kamiNoKuStyle = args.kamiNoKuStyle,
                    shimoNoKuStyle = args.shimoNoKuStyle,
                    inputSecond = args.inputSecond,
                    animationSpeed = args.animationSpeed,
                    referer = args.referer
                )
                findNavController().navigate(action)
            }
        }
        binding.buttonGoToResult.setOnClickListener {
            when (args.referer) {
                Referer.Training -> {
                    val action = AnswerFragmentDirections.actionAnswerToTrainingResult(
                        kamiNoKuStyle = args.kamiNoKuStyle,
                        shimoNoKuStyle = args.shimoNoKuStyle,
                        inputSecond = args.inputSecond,
                        animationSpeed = args.animationSpeed
                    )
                    findNavController().navigate(action)
                }
                Referer.Exam -> {
                    val action = AnswerFragmentDirections.actionAnswerToExamFinisher()
                    findNavController().navigate(action)
                }
            }
        }
        binding.textMaterial.setOnClickListener {
            val action = AnswerFragmentDirections.actionAnswerToMaterialDetailPage(
                material = args.correctKaruta
            )
            findNavController().navigate(action)
        }
    }
}
