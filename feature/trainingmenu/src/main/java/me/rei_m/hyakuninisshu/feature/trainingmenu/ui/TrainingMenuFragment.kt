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
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.setUp
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.setUpDropDown
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import me.rei_m.hyakuninisshu.feature.trainingmenu.R
import me.rei_m.hyakuninisshu.feature.trainingmenu.databinding.TrainingMenuFragmentBinding
import me.rei_m.hyakuninisshu.feature.trainingmenu.di.TrainingMenuComponent
import me.rei_m.hyakuninisshu.state.training.model.ColorCondition
import me.rei_m.hyakuninisshu.state.training.model.DisplayAnimationSpeedCondition
import me.rei_m.hyakuninisshu.state.training.model.DisplayStyleCondition
import me.rei_m.hyakuninisshu.state.training.model.InputSecondCondition
import me.rei_m.hyakuninisshu.state.training.model.KimarijiCondition
import me.rei_m.hyakuninisshu.state.training.model.RangeFromCondition
import me.rei_m.hyakuninisshu.state.training.model.RangeToCondition
import javax.inject.Inject

class TrainingMenuFragment : Fragment() {

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    private var _binding: TrainingMenuFragmentBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<TrainingMenuViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        (requireActivity() as TrainingMenuComponent.Injector)
            .trainingMenuComponent()
            .create()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = TrainingMenuFragmentBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyticsHelper.sendScreenView("TrainingMenu", requireActivity())
        binding.dropdownRangeFrom.setUpDropDown(
            RangeFromCondition.values().map { it.label(resources) }
        )
        binding.dropdownRangeTo.setUpDropDown(
            RangeToCondition.values().map { it.label(resources) }
        )
        binding.dropdownKimariji.setUpDropDown(
            KimarijiCondition.values().map { it.label(resources) }
        )
        binding.dropdownTopPhraseStyle.setUpDropDown(
            DisplayStyleCondition.values().map { it.label(resources) }
        )
        binding.dropdownBottomPhraseStyle.setUpDropDown(
            DisplayStyleCondition.values().map { it.label(resources) }
        )
        binding.dropdownColor.setUpDropDown(
            ColorCondition.values().map { it.label(resources) }
        )
        binding.dropdownInputSecond.setUpDropDown(
            InputSecondCondition.values().map { it.label(resources) }
        )
        binding.dropdownAnimationSpeed.setUpDropDown(
            DisplayAnimationSpeedCondition.values().map { it.label(resources) }
        )

        binding.buttonStartTraining.setOnClickListener {
            if (viewModel.rangeTo.ordinal < viewModel.rangeFrom.ordinal) {
                Snackbar.make(
                    binding.root,
                    R.string.text_message_invalid_training_range,
                    Snackbar.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            analyticsHelper.logActionEvent(AnalyticsHelper.ActionEvent.START_TRAINING)

            val action = TrainingMenuFragmentDirections.actionTrainingMenuToTrainingStarter(
                rangeFrom = viewModel.rangeFrom,
                rangeTo = viewModel.rangeTo,
                kimariji = viewModel.kimariji,
                color = viewModel.color,
                kamiNoKuStyle = viewModel.kamiNoKuStyle,
                shimoNoKuStyle = viewModel.shimoNoKuStyle,
                inputSecond = viewModel.inputSecond,
                animationSpeed = viewModel.animationSpeed
            )
            findNavController().navigate(action)
        }

        binding.dropdownRangeFrom.setUp(viewModel.rangeFrom.label(resources)) {
            viewModel.rangeFrom = RangeFromCondition.values()[it]
        }
        binding.dropdownRangeTo.setUp(viewModel.rangeTo.label(resources)) {
            viewModel.rangeTo = RangeToCondition.values()[it]
        }
        binding.dropdownKimariji.setUp(viewModel.kimariji.label(resources)) {
            viewModel.kimariji = KimarijiCondition.values()[it]
        }
        binding.dropdownTopPhraseStyle.setUp(viewModel.kamiNoKuStyle.label(resources)) {
            viewModel.kamiNoKuStyle = DisplayStyleCondition.values()[it]
        }
        binding.dropdownBottomPhraseStyle.setUp(viewModel.shimoNoKuStyle.label(resources)) {
            viewModel.shimoNoKuStyle = DisplayStyleCondition.values()[it]
        }
        binding.dropdownColor.setUp(viewModel.color.label(resources)) {
            viewModel.color = ColorCondition.values()[it]
        }
        binding.dropdownInputSecond.setUp(viewModel.inputSecond.label(resources)) {
            viewModel.inputSecond = InputSecondCondition.values()[it]
        }
        binding.dropdownAnimationSpeed.setUp(viewModel.animationSpeed.label(resources)) {
            viewModel.animationSpeed = DisplayAnimationSpeedCondition.values()[it]
        }
    }
}
