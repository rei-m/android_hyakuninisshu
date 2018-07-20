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

package me.rei_m.hyakuninisshu.presentation.entrance

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.AnalyticsManager
import me.rei_m.hyakuninisshu.databinding.FragmentTrainingMenuBinding
import me.rei_m.hyakuninisshu.di.ForFragment
import me.rei_m.hyakuninisshu.presentation.enums.*
import me.rei_m.hyakuninisshu.presentation.widget.adapter.SpinnerAdapter
import javax.inject.Inject

class TrainingMenuFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: TrainingMenuViewModel.Factory

    @Inject
    lateinit var analyticsManager: AnalyticsManager

    lateinit var binding: FragmentTrainingMenuBinding

    lateinit var viewModel: TrainingMenuViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (savedInstanceState != null) {
            with(viewModelFactory) {
                trainingRangeFrom = TrainingRangeFrom[savedInstanceState.getInt(KEY_TRAINING_RANGE_FROM)]
                trainingRangeTo = TrainingRangeTo[savedInstanceState.getInt(KEY_TRAINING_RANGE_TO)]
                kimariji = KimarijiFilter[savedInstanceState.getInt(KEY_KIMARIJI)]
                kamiNoKuStyle = KarutaStyleFilter[savedInstanceState.getInt(KEY_KAMI_NO_KU_STYLE)]
                shimoNoKuStyle = KarutaStyleFilter[savedInstanceState.getInt(KEY_SHIMO_NO_KU_STYLE)]
                color = ColorFilter[savedInstanceState.getInt(KEY_COLOR)]
            }
        }
        viewModel = viewModelFactory.create()
        viewModel.snackBarMessage.observe(this, Observer {
            it ?: return@Observer
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        })

        binding = FragmentTrainingMenuBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@TrainingMenuFragment)

            trainingRangeFromAdapter = SpinnerAdapter.newInstance(root.context, TrainingRangeFrom.values().asList())
            trainingRangeToAdapter = SpinnerAdapter.newInstance(root.context, TrainingRangeTo.values().asList())
            kimarijiAdapter = SpinnerAdapter.newInstance(root.context, KimarijiFilter.values().asList())
            karutaStyleAdapter = SpinnerAdapter.newInstance(root.context, KarutaStyleFilter.values().asList())
            colorAdapter = SpinnerAdapter.newInstance(root.context, ColorFilter.values().asList())
        }

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onResume() {
        analyticsManager.logScreenEvent(AnalyticsManager.ScreenEvent.TRAINING_MENU)
        super.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_TRAINING_RANGE_FROM, viewModel.trainingRangeFrom.value!!.ordinal)
        outState.putInt(KEY_TRAINING_RANGE_TO, viewModel.trainingRangeTo.value!!.ordinal)
        outState.putInt(KEY_KIMARIJI, viewModel.kimariji.value!!.ordinal)
        outState.putInt(KEY_KAMI_NO_KU_STYLE, viewModel.kamiNoKuStyle.value!!.ordinal)
        outState.putInt(KEY_SHIMO_NO_KU_STYLE, viewModel.shimoNoKuStyle.value!!.ordinal)
        outState.putInt(KEY_COLOR, viewModel.color.value!!.ordinal)
        super.onSaveInstanceState(outState)
    }

    @dagger.Module
    abstract class Module {
        @ForFragment
        @ContributesAndroidInjector
        abstract fun contributeInjector(): TrainingMenuFragment
    }

    companion object {

        val TAG: String = TrainingMenuFragment::class.java.simpleName

        private const val KEY_TRAINING_RANGE_FROM = "trainingRangeFrom"

        private const val KEY_TRAINING_RANGE_TO = "trainingRangeTo"

        private const val KEY_KIMARIJI = "kimarij"

        private const val KEY_COLOR = "color"

        private const val KEY_KAMI_NO_KU_STYLE = "kamiNoKuStyle"

        private const val KEY_SHIMO_NO_KU_STYLE = "shimoNoKuStyle"

        fun newInstance(): TrainingMenuFragment = TrainingMenuFragment()
    }
}
