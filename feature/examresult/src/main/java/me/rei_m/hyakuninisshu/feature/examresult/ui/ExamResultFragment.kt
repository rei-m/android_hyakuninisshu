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

package me.rei_m.hyakuninisshu.feature.examresult.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import me.rei_m.hyakuninisshu.feature.examresult.databinding.ExamResultFragmentBinding
import me.rei_m.hyakuninisshu.feature.examresult.di.ExamResultComponent
import me.rei_m.hyakuninisshu.feature.examresult.ui.widget.ExamResultView
import javax.inject.Inject

class ExamResultFragment : Fragment() {
    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    @Inject
    lateinit var viewModelFactory: ExamResultViewModel.Factory

    private val args: ExamResultFragmentArgs by navArgs()

    private var _binding: ExamResultFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ExamResultViewModel> {
        viewModelFactory.apply {
            examId = args.examId
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity() as ExamResultComponent.Injector)
            .examResultComponent()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ExamResultFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        binding.viewResult.listener = null
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyticsHelper.sendScreenView("ExamResult", requireActivity())

        binding.buttonBack.setOnClickListener {
            val action = ExamResultFragmentDirections.actionExamResultPop()
            findNavController().navigate(action)
        }

        binding.viewModel = viewModel
        viewModel.materialMap.observe(viewLifecycleOwner, {
            binding.viewResult.listener = object : ExamResultView.OnClickItemListener {
                override fun onClick(karutaNo: Int) {
                    val material = it[karutaNo] ?: return
                    val action =
                        ExamResultFragmentDirections.actionExamResultToMaterialDetailPage(material)
                    findNavController().navigate(action)
                }
            }
        })
    }
}
