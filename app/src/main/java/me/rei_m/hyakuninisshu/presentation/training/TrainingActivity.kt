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

import android.app.Activity
import android.arch.lifecycle.Observer
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.FragmentTransaction
import android.view.ViewGroup
import android.widget.RelativeLayout
import dagger.Binds
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.multibindings.IntoMap
import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.databinding.ActivityTrainingBinding
import me.rei_m.hyakuninisshu.di.ForActivity
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.ext.replaceFragment
import me.rei_m.hyakuninisshu.ext.setupActionBar
import me.rei_m.hyakuninisshu.presentation.core.CoreInteractionListener
import me.rei_m.hyakuninisshu.presentation.core.QuizAnswerFragment
import me.rei_m.hyakuninisshu.presentation.core.QuizFragment
import me.rei_m.hyakuninisshu.presentation.di.ActivityModule
import me.rei_m.hyakuninisshu.presentation.enums.*
import me.rei_m.hyakuninisshu.presentation.widget.ad.AdViewObserver
import me.rei_m.hyakuninisshu.presentation.widget.dialog.AlertDialogFragment
import me.rei_m.hyakuninisshu.util.EventObserver
import javax.inject.Inject

class TrainingActivity : DaggerAppCompatActivity(),
    AlertDialogFragment.OnDialogInteractionListener,
    CoreInteractionListener {

    @Inject
    lateinit var viewModelFactory: TrainingViewModel.Factory

    @Inject
    lateinit var adViewObserver: AdViewObserver

    private lateinit var viewModel: TrainingViewModel

    private lateinit var binding: ActivityTrainingBinding

    override val kamiNoKuStyle by lazy {
        KarutaStyleFilter[intent.getIntExtra(ARG_KAMI_NO_KU_STYLE, 0)]
    }

    override val shimoNoKuStyle by lazy {
        KarutaStyleFilter[intent.getIntExtra(ARG_SHIMO_NO_KU_STYLE, 0)]
    }

    private val trainingRangeFrom by lazy {
        TrainingRangeFrom[intent.getIntExtra(ARG_TRAINING_RANGE_FROM, 0)]
    }

    private val trainingRangeTo by lazy {
        TrainingRangeTo[intent.getIntExtra(ARG_TRAINING_RANGE_TO, 0)]
    }

    private val kimarijiFilter by lazy {
        KimarijiFilter[intent.getIntExtra(ARG_KIMARIJI, 0)]
    }

    private val colorFilter by lazy {
        ColorFilter[intent.getIntExtra(ARG_COLOR, 0)]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModelFactory.create(this)
        with(viewModel) {
            isVisibleAd.observe(this@TrainingActivity, Observer {
                if (it == true) {
                    adViewObserver.showAd()
                } else {
                    adViewObserver.hideAd()
                }
            })
            currentKarutaQuizId.observe(this@TrainingActivity, Observer {
                it ?: return@Observer
                onReceiveKarutaQuizId(it)
            })
            unhandledErrorEvent.observe(this@TrainingActivity, EventObserver {
                onErrorQuiz()
            })
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_training)
        binding.setLifecycleOwner(this)

        setupActionBar(binding.toolbar) {
        }
        setupAd()

        binding.viewModel = viewModel

        if (supportFragmentManager.fragments.isEmpty()) {
            viewModel.startTraining(trainingRangeFrom, trainingRangeTo, kimarijiFilter, colorFilter)
        }
    }

    override fun onAnswered(quizId: KarutaQuizIdentifier) {
        openAnswer(quizId)
    }

    override fun onGoToNext() {
        viewModel.fetchNext()
    }

    override fun onGoToResult() {
        if (supportFragmentManager.findFragmentByTag(TrainingResultFragment.TAG) == null) {
            replaceFragment(
                R.id.content,
                TrainingResultFragment.newInstance(),
                TrainingResultFragment.TAG,
                FragmentTransaction.TRANSIT_FRAGMENT_FADE
            )
        }
    }

    override fun onErrorQuiz() {
        showError()
    }

    override fun onAlertPositiveClick() {
        finish()
    }

    override fun onAlertNegativeClick() {

    }

    private fun setupAd() {
        lifecycle.addObserver(adViewObserver)
        val adView = adViewObserver.adView()

        val params = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, adView.id)
        }
        adView.layoutParams = params
        binding.root.addView(adView)

        if (viewModel.isVisibleAd.value == true) {
            adViewObserver.showAd()
        } else {
            adViewObserver.hideAd()
        }

        adViewObserver.loadAd()
    }

    @ForActivity
    @dagger.Subcomponent(modules = [
        ActivityModule::class,
        QuizFragment.Module::class,
        QuizAnswerFragment.Module::class,
        TrainingResultFragment.Module::class
    ])
    interface Subcomponent : AndroidInjector<TrainingActivity> {

        @dagger.Subcomponent.Builder
        abstract class Builder : AndroidInjector.Builder<TrainingActivity>() {

            abstract fun activityModule(module: ActivityModule): Builder

            override fun seedInstance(instance: TrainingActivity) {
                activityModule(ActivityModule(instance))
            }
        }
    }

    @dagger.Module(subcomponents = [Subcomponent::class])
    abstract class Module {
        @Binds
        @IntoMap
        @ActivityKey(TrainingActivity::class)
        abstract fun bind(builder: Subcomponent.Builder): AndroidInjector.Factory<out Activity>
    }

    companion object {

        private const val ARG_TRAINING_RANGE_FROM = "trainingRangeFrom"

        private const val ARG_TRAINING_RANGE_TO = "trainingRangeTo"

        private const val ARG_KIMARIJI = "kimarij"

        private const val ARG_COLOR = "color"

        private const val ARG_KAMI_NO_KU_STYLE = "kamiNoKuStyle"

        private const val ARG_SHIMO_NO_KU_STYLE = "shimoNoKuStyle"

        fun createIntent(context: Context,
                         trainingRangeFrom: TrainingRangeFrom,
                         trainingRangeTo: TrainingRangeTo,
                         kimarijiFilter: KimarijiFilter,
                         colorFilter: ColorFilter,
                         kamiNoKuStyle: KarutaStyleFilter,
                         shimoNoKuStyle: KarutaStyleFilter): Intent {
            val intent = Intent(context, TrainingActivity::class.java)
            val bundle = Bundle()
            bundle.putInt(ARG_TRAINING_RANGE_FROM, trainingRangeFrom.ordinal)
            bundle.putInt(ARG_TRAINING_RANGE_TO, trainingRangeTo.ordinal)
            bundle.putInt(ARG_KIMARIJI, kimarijiFilter.ordinal)
            bundle.putInt(ARG_COLOR, colorFilter.ordinal)
            bundle.putInt(ARG_KAMI_NO_KU_STYLE, kamiNoKuStyle.ordinal)
            bundle.putInt(ARG_SHIMO_NO_KU_STYLE, shimoNoKuStyle.ordinal)
            intent.putExtras(bundle)
            return intent
        }
    }
}
