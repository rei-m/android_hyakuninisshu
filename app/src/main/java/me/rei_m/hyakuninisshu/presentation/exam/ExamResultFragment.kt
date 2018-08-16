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

package me.rei_m.hyakuninisshu.presentation.exam

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.databinding.FragmentExamResultBinding
import me.rei_m.hyakuninisshu.di.ForFragment
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaExamIdentifier
import me.rei_m.hyakuninisshu.util.AnalyticsHelper
import javax.inject.Inject

class ExamResultFragment : DaggerFragment() {

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    @Inject
    lateinit var viewModelFactory: ExamResultViewModel.Factory

    private lateinit var examResultViewModel: ExamResultViewModel

    private lateinit var binding: FragmentExamResultBinding

    private var listener: OnFragmentInteractionListener? = null

    private var karutaExamId: KarutaExamIdentifier? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState != null) {
            viewModelFactory.initialKarutaExamId = savedInstanceState.getParcelable(KEY_EXAM_ID)
        }
        examResultViewModel = viewModelFactory.create(requireActivity())

        binding = FragmentExamResultBinding.inflate(inflater, container, false).apply {
            viewModel = examResultViewModel
            setLifecycleOwner(this@ExamResultFragment)
        }

        examResultViewModel.karutaExamId.observe(this, Observer {
            karutaExamId = it
        })
        examResultViewModel.notFoundExamError.observe(this, Observer {
            listener?.onErrorFinish()
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyticsHelper.sendScreenView("ExamResult", requireActivity())

        binding.viewResult.onClickKarutaEvent.observe(this, Observer {
            it ?: return@Observer
            examResultViewModel.openKaruta(it)
        })
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        karutaExamId?.let { outState.putParcelable(KEY_EXAM_ID, it) }
        super.onSaveInstanceState(outState)
    }

    @dagger.Module
    abstract class Module {
        @ForFragment
        @ContributesAndroidInjector
        abstract fun contributeInjector(): ExamResultFragment
    }

    interface OnFragmentInteractionListener {
        fun onErrorFinish()
    }

    companion object {

        const val TAG: String = "ExamResultFragment"

        private const val KEY_EXAM_ID = "examId"

        fun newInstance() = ExamResultFragment()
    }
}
