/*
 * Copyright (c) 2019. Rei Matsushita
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
package me.rei_m.hyakuninisshu.feature.trainingmenu.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.feature.corecomponent.di.FragmentScope
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.*
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.provideActivityViewModel
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.adapter.SpinnerAdapter
import me.rei_m.hyakuninisshu.feature.trainingmenu.R
import me.rei_m.hyakuninisshu.feature.trainingmenu.databinding.FragmentTrainingMenuBinding
import me.rei_m.hyakuninisshu.feature.trainingmenu.helper.Navigator
import javax.inject.Inject

class TrainingMenuFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: TrainingMenuViewModel.Factory

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    private lateinit var binding: FragmentTrainingMenuBinding

    private lateinit var viewModel: TrainingMenuViewModel

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
                animationSpeed = QuizAnimationSpeed[savedInstanceState.getInt(KEY_ANIMATION_SPEED)]
            }
        }
        viewModel = provideActivityViewModel(TrainingMenuViewModel::class.java, viewModelFactory)

        binding = FragmentTrainingMenuBinding.inflate(inflater, container, false).apply {
            trainingRangeFromAdapter = SpinnerAdapter.newInstance(root.context, TrainingRangeFrom.values().asList())
            trainingRangeToAdapter = SpinnerAdapter.newInstance(root.context, TrainingRangeTo.values().asList())
            kimarijiAdapter = SpinnerAdapter.newInstance(root.context, KimarijiFilter.values().asList())
            karutaStyleAdapter = SpinnerAdapter.newInstance(root.context, KarutaStyleFilter.values().asList())
            colorAdapter = SpinnerAdapter.newInstance(root.context, ColorFilter.values().asList())
            animationSpeedAdapter = SpinnerAdapter.newInstance(root.context, QuizAnimationSpeed.values().asList())
        }
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyticsHelper.sendScreenView("Entrance - TrainingMenu", requireActivity())

        binding.buttonStartTraining.setOnClickListener {
            if (viewModel.trainingRangeFrom.value!!.ordinal > viewModel.trainingRangeTo.value!!.ordinal) {
                Snackbar.make(binding.root, R.string.text_message_invalid_training_range, Snackbar.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            analyticsHelper.logActionEvent(AnalyticsHelper.ActionEvent.START_TRAINING)
            navigator.navigateToTraining(
                viewModel.trainingRangeFrom.value!!,
                viewModel.trainingRangeTo.value!!,
                viewModel.kimariji.value!!,
                viewModel.color.value!!,
                viewModel.kamiNoKuStyle.value!!,
                viewModel.shimoNoKuStyle.value!!,
                viewModel.animationSpeed.value!!
            )
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_TRAINING_RANGE_FROM, viewModel.trainingRangeFrom.value!!.ordinal)
        outState.putInt(KEY_TRAINING_RANGE_TO, viewModel.trainingRangeTo.value!!.ordinal)
        outState.putInt(KEY_KIMARIJI, viewModel.kimariji.value!!.ordinal)
        outState.putInt(KEY_KAMI_NO_KU_STYLE, viewModel.kamiNoKuStyle.value!!.ordinal)
        outState.putInt(KEY_SHIMO_NO_KU_STYLE, viewModel.shimoNoKuStyle.value!!.ordinal)
        outState.putInt(KEY_COLOR, viewModel.color.value!!.ordinal)
        outState.putInt(KEY_ANIMATION_SPEED, viewModel.animationSpeed.value!!.ordinal)
        super.onSaveInstanceState(outState)
    }

    @dagger.Module
    abstract class Module {
        @FragmentScope
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

        private const val KEY_ANIMATION_SPEED = "animationSpeed"

        fun newInstance() = TrainingMenuFragment()
    }
}
