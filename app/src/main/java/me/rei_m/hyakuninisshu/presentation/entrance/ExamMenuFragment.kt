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

package me.rei_m.hyakuninisshu.presentation.entrance

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.AnalyticsManager
import me.rei_m.hyakuninisshu.databinding.FragmentExamMenuBinding
import me.rei_m.hyakuninisshu.di.ForFragment
import me.rei_m.hyakuninisshu.ext.FragmentExt
import javax.inject.Inject

class ExamMenuFragment : DaggerFragment(), FragmentExt {

    @Inject
    lateinit var storeFactory: EntranceStore.Factory

    @Inject
    lateinit var viewModelFactory: ExamMenuViewModel.Factory

    @Inject
    lateinit var analyticsManager: AnalyticsManager

    lateinit var binding: FragmentExamMenuBinding

    lateinit var viewModel: ExamMenuViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val store = obtainActivityStore(EntranceStore::class.java, storeFactory)
        viewModel = viewModelFactory.create(store)
        viewModel.start()

        binding = FragmentExamMenuBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@ExamMenuFragment)
        }

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onResume() {
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.EXAM)
        super.onResume()
    }

    @dagger.Module
    abstract class Module {
        @ForFragment
        @ContributesAndroidInjector
        abstract fun contributeInjector(): ExamMenuFragment
    }

    companion object {

        val TAG: String = ExamMenuFragment::class.java.simpleName

        fun newInstance(): ExamMenuFragment = ExamMenuFragment()
    }
}
