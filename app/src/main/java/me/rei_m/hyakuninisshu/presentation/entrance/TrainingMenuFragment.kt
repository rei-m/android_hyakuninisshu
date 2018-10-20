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

/* ktlint-disable package-name */
package me.rei_m.hyakuninisshu.presentation.entrance

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.databinding.FragmentTrainingMenuBinding
import me.rei_m.hyakuninisshu.di.ForFragment
import me.rei_m.hyakuninisshu.presentation.enums.ColorFilter
import me.rei_m.hyakuninisshu.presentation.enums.KarutaStyleFilter
import me.rei_m.hyakuninisshu.presentation.enums.KimarijiFilter
import me.rei_m.hyakuninisshu.presentation.enums.TrainingRangeFrom
import me.rei_m.hyakuninisshu.presentation.enums.TrainingRangeTo
import me.rei_m.hyakuninisshu.presentation.widget.adapter.SpinnerAdapter
import me.rei_m.hyakuninisshu.util.AnalyticsHelper
import me.rei_m.hyakuninisshu.util.EventObserver
import javax.inject.Inject

class TrainingMenuFragment : DaggerFragment() {

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    @Inject
    lateinit var viewModelFactory: TrainingMenuViewModel.Factory

    private lateinit var binding: FragmentTrainingMenuBinding

    private lateinit var trainingMenuViewModel: TrainingMenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
        trainingMenuViewModel = viewModelFactory.create()

        binding = FragmentTrainingMenuBinding.inflate(inflater, container, false).apply {
            viewModel = trainingMenuViewModel
            setLifecycleOwner(this@TrainingMenuFragment)

            trainingRangeFromAdapter = SpinnerAdapter.newInstance(root.context, TrainingRangeFrom.values().asList())
            trainingRangeToAdapter = SpinnerAdapter.newInstance(root.context, TrainingRangeTo.values().asList())
            kimarijiAdapter = SpinnerAdapter.newInstance(root.context, KimarijiFilter.values().asList())
            karutaStyleAdapter = SpinnerAdapter.newInstance(root.context, KarutaStyleFilter.values().asList())
            colorAdapter = SpinnerAdapter.newInstance(root.context, ColorFilter.values().asList())
        }

        trainingMenuViewModel.snackBarMessage.observe(this, EventObserver {
            Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
        })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyticsHelper.sendScreenView("Entrance - TrainingMenu", requireActivity())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_TRAINING_RANGE_FROM, trainingMenuViewModel.trainingRangeFrom.value!!.ordinal)
        outState.putInt(KEY_TRAINING_RANGE_TO, trainingMenuViewModel.trainingRangeTo.value!!.ordinal)
        outState.putInt(KEY_KIMARIJI, trainingMenuViewModel.kimariji.value!!.ordinal)
        outState.putInt(KEY_KAMI_NO_KU_STYLE, trainingMenuViewModel.kamiNoKuStyle.value!!.ordinal)
        outState.putInt(KEY_SHIMO_NO_KU_STYLE, trainingMenuViewModel.shimoNoKuStyle.value!!.ordinal)
        outState.putInt(KEY_COLOR, trainingMenuViewModel.color.value!!.ordinal)
        super.onSaveInstanceState(outState)
    }

    @dagger.Module
    abstract class Module {
        @ForFragment
        @ContributesAndroidInjector
        abstract fun contributeInjector(): TrainingMenuFragment
    }

    companion object {

        const val TAG: String = "TrainingMenuFragment"

        private const val KEY_TRAINING_RANGE_FROM = "trainingRangeFrom"

        private const val KEY_TRAINING_RANGE_TO = "trainingRangeTo"

        private const val KEY_KIMARIJI = "kimarij"

        private const val KEY_COLOR = "color"

        private const val KEY_KAMI_NO_KU_STYLE = "kamiNoKuStyle"

        private const val KEY_SHIMO_NO_KU_STYLE = "shimoNoKuStyle"

        fun newInstance() = TrainingMenuFragment()
    }
}
