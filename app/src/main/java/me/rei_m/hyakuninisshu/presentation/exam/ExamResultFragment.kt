/*
 * Copyright (c) 2017. Rei Matsushita
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
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.AnalyticsManager
import me.rei_m.hyakuninisshu.databinding.FragmentExamResultBinding
import me.rei_m.hyakuninisshu.di.ForFragment
import me.rei_m.hyakuninisshu.ext.FragmentExt
import javax.inject.Inject

class ExamResultFragment : DaggerFragment(), FragmentExt {

    @Inject
    lateinit var storeFactory: ExamStore.Factory

    @Inject
    lateinit var viewModelFactory: ExamResultViewModel.Factory

    @Inject
    lateinit var analyticsManager: AnalyticsManager

    private lateinit var viewModel: ExamResultViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (savedInstanceState != null) {
            viewModelFactory.initialKarutaExamId = savedInstanceState.getParcelable(KEY_EXAM_ID)
        }
        viewModel = viewModelFactory.create(obtainActivityStore(ExamStore::class.java, storeFactory))
        viewModel.start()

        val binding = FragmentExamResultBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@ExamResultFragment)
        }

        binding.viewResult.onClickKarutaEvent.observe(this, Observer {
            it ?: return@Observer
            viewModel.openKaruta(it)
        })

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onResume() {
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.EXAM_RESULT)
        super.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        viewModel.karutaExamId.value?.let { outState.putParcelable(KEY_EXAM_ID, it) }
        super.onSaveInstanceState(outState)
    }

    @dagger.Module
    abstract class Module {
        @ForFragment
        @ContributesAndroidInjector
        abstract fun contributeInjector(): ExamResultFragment
    }

    companion object {

        val TAG: String = ExamResultFragment::class.java.simpleName

        private const val KEY_EXAM_ID = "examId"

        fun newInstance(): ExamResultFragment = ExamResultFragment()
    }
}
