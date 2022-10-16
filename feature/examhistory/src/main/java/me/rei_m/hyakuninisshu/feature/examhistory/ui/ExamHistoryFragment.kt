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

package me.rei_m.hyakuninisshu.feature.examhistory.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import me.rei_m.hyakuninisshu.feature.examhistory.databinding.ExamHistoryFragmentBinding
import me.rei_m.hyakuninisshu.feature.examhistory.di.ExamHistoryComponent
import me.rei_m.hyakuninisshu.feature.examhistory.ui.widget.ExamResultListAdapter
import javax.inject.Inject

class ExamHistoryFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: ExamHistoryViewModel.Factory

    private var _binding: ExamHistoryFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ExamHistoryViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity() as ExamHistoryComponent.Injector)
            .examHistoryComponent()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ExamHistoryFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerExamResultList.adapter = ExamResultListAdapter(requireContext(), listOf())
        binding.viewModel = viewModel
        viewModel.resultList.observe(viewLifecycleOwner, Observer {
            it ?: return@Observer
            (binding.recyclerExamResultList.adapter as ExamResultListAdapter).replaceData(it)
        })
    }
}
