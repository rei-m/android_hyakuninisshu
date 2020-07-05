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

package me.rei_m.hyakuninisshu.feature.question.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.windowSize
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import me.rei_m.hyakuninisshu.feature.question.databinding.QuestionFragmentBinding
import me.rei_m.hyakuninisshu.feature.question.di.QuestionComponent
import me.rei_m.hyakuninisshu.state.question.model.QuestionState
import javax.inject.Inject

class QuestionFragment : Fragment() {
    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    @Inject
    lateinit var viewModelFactory: QuestionViewModel.Factory

    private val args: QuestionFragmentArgs by navArgs()

    private var _binding: QuestionFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<QuestionViewModel> {
        viewModelFactory.apply {
            questionId = args.questionId
            kamiNoKuStyle = args.kamiNoKuStyle
            shimoNoKuStyle = args.shimoNoKuStyle
            inputSecond = args.inputSecond
        }
    }

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
    ): View? {
        _binding = QuestionFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyticsHelper.sendScreenView("Question", requireActivity())

        // 問題を縦表示スクロールなしで収めるための微妙な対応
        val windowSize = requireActivity().windowSize()

        val yomiFudaHeight = windowSize.y / 2
        val yomiFudaTextSize = yomiFudaHeight / 13

        binding.yomiFudaTextSize = yomiFudaTextSize

        val toriFudaWidth = windowSize.x / 4
        val toriFudaTextSize = toriFudaWidth / 5

        binding.toriFudaTextSize = toriFudaTextSize
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.viewModel = viewModel

        viewModel.yomiFudaWithState.observe(viewLifecycleOwner, Observer {
            val (yomiFuda, state) = it
            when (state) {
                is QuestionState.InAnswer -> {
                    binding.viewYomiFuda.startDisplayWithAnimation(
                        yomiFuda,
                        args.animationSpeed.value
                    )
                }
                is QuestionState.Answered -> {
                    binding.viewYomiFuda.stopAnimation()
                }
                else -> {
                }
            }
        })

        binding.layoutQuestionResult.setOnClickListener {
            val state = viewModel.state.value
            if (state is QuestionState.Answered) {
                val action = QuestionFragmentDirections.actionQuestionToAnswer(
                    nextQuestionId = state.nextQuestionId,
                    correctKaruta = state.correctMaterial,
                    kamiNoKuStyle = args.kamiNoKuStyle,
                    shimoNoKuStyle = args.shimoNoKuStyle,
                    inputSecond = args.inputSecond,
                    animationSpeed = args.animationSpeed,
                    referer = args.referer
                )
                findNavController().navigate(action)
            }
        }
    }

    override fun onPause() {
        binding.viewYomiFuda.stopAnimation()
        super.onPause()
    }
}
