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

package me.rei_m.hyakuninisshu.presentation.training

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.databinding.FragmentTrainingResultBinding
import me.rei_m.hyakuninisshu.di.ForFragment
import me.rei_m.hyakuninisshu.ext.FragmentExt
import javax.inject.Inject

class TrainingResultFragment : DaggerFragment(), FragmentExt {

    @Inject
    lateinit var storeFactory: TrainingStore.Factory

    @Inject
    lateinit var viewModelFactory: TrainingResultViewModel.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewModel = viewModelFactory.create(obtainActivityStore(TrainingStore::class.java, storeFactory))

        val binding = FragmentTrainingResultBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@TrainingResultFragment)
        }
        binding.viewModel = viewModel

        return binding.root
    }

    @dagger.Module
    abstract class Module {
        @ForFragment
        @ContributesAndroidInjector
        abstract fun contributeInjector(): TrainingResultFragment
    }

    companion object {

        val TAG: String = TrainingResultFragment::class.java.simpleName

        fun newInstance(): TrainingResultFragment = TrainingResultFragment()
    }
}
