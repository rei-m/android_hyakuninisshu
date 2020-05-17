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
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.android.material.snackbar.Snackbar
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.feature.corecomponent.di.FragmentScope
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.ColorFilter
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.KarutaStyleFilter
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.KimarijiFilter
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.QuizAnimationSpeed
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.SpinnerItem
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.TrainingRangeFrom
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.TrainingRangeTo
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.provideActivityViewModel
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
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

    private var _binding: FragmentTrainingMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: TrainingMenuViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (savedInstanceState != null) {
            with(viewModelFactory) {
                trainingRangeFrom =
                    TrainingRangeFrom[savedInstanceState.getInt(KEY_TRAINING_RANGE_FROM)]
                trainingRangeTo = TrainingRangeTo[savedInstanceState.getInt(KEY_TRAINING_RANGE_TO)]
                kimariji = KimarijiFilter[savedInstanceState.getInt(KEY_KIMARIJI)]
                kamiNoKuStyle = KarutaStyleFilter[savedInstanceState.getInt(KEY_KAMI_NO_KU_STYLE)]
                shimoNoKuStyle = KarutaStyleFilter[savedInstanceState.getInt(KEY_SHIMO_NO_KU_STYLE)]
                color = ColorFilter[savedInstanceState.getInt(KEY_COLOR)]
                animationSpeed = QuizAnimationSpeed[savedInstanceState.getInt(KEY_ANIMATION_SPEED)]
            }
        }
        viewModel = provideActivityViewModel(TrainingMenuViewModel::class.java, viewModelFactory)

        _binding = FragmentTrainingMenuBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyticsHelper.sendScreenView("Entrance - TrainingMenu", requireActivity())

        binding.buttonStartTraining.setOnClickListener {
            if (viewModel.trainingRangeFrom.ordinal > viewModel.trainingRangeTo.ordinal) {
                Snackbar.make(
                    binding.root,
                    R.string.text_message_invalid_training_range,
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            analyticsHelper.logActionEvent(AnalyticsHelper.ActionEvent.START_TRAINING)
            navigator.navigateToTraining(
                viewModel.trainingRangeFrom,
                viewModel.trainingRangeTo,
                viewModel.kimariji,
                viewModel.color,
                viewModel.kamiNoKuStyle,
                viewModel.shimoNoKuStyle,
                viewModel.animationSpeed
            )
        }

        binding.dropdownRangeFrom.setUp(TrainingRangeFrom.values(), viewModel.trainingRangeFrom) {
            viewModel.trainingRangeFrom = it
        }

        binding.dropdownRangeTo.setUp(TrainingRangeTo.values(), viewModel.trainingRangeTo) {
            viewModel.trainingRangeTo = it
        }

        binding.dropdownKimariji.setUp(KimarijiFilter.values(), viewModel.kimariji) {
            viewModel.kimariji = it
        }

        binding.dropdownColor.setUp(ColorFilter.values(), viewModel.color) {
            viewModel.color = it
        }

        binding.dropdownTopPhraseStyle.setUp(KarutaStyleFilter.values(), viewModel.kamiNoKuStyle) {
            viewModel.kamiNoKuStyle = it
        }

        binding.dropdownBottomPhraseStyle.setUp(
            KarutaStyleFilter.values(),
            viewModel.shimoNoKuStyle
        ) {
            viewModel.shimoNoKuStyle = it
        }

        binding.dropdownAnimationSpeed.setUp(
            QuizAnimationSpeed.values(),
            viewModel.animationSpeed
        ) {
            viewModel.animationSpeed = it
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(KEY_TRAINING_RANGE_FROM, viewModel.trainingRangeFrom.ordinal)
        outState.putInt(KEY_TRAINING_RANGE_TO, viewModel.trainingRangeTo.ordinal)
        outState.putInt(KEY_KIMARIJI, viewModel.kimariji.ordinal)
        outState.putInt(KEY_KAMI_NO_KU_STYLE, viewModel.kamiNoKuStyle.ordinal)
        outState.putInt(KEY_SHIMO_NO_KU_STYLE, viewModel.shimoNoKuStyle.ordinal)
        outState.putInt(KEY_COLOR, viewModel.color.ordinal)
        outState.putInt(KEY_ANIMATION_SPEED, viewModel.animationSpeed.ordinal)
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

private fun <T : SpinnerItem> AutoCompleteTextView.setUp(
    itemList: Array<T>,
    initialValue: T,
    onSelected: (value: T) -> Unit
) {
    inputType = InputType.TYPE_NULL
    keyListener = null

    setText(initialValue.label(resources))

    ArrayAdapter(
        context,
        R.layout.dropdown_menu_popup_item,
        itemList.map { it.label(resources) }
    ).let {
        setAdapter(it)
    }

    setOnItemClickListener { _, _, position, _ -> onSelected(itemList[position]) }
}
