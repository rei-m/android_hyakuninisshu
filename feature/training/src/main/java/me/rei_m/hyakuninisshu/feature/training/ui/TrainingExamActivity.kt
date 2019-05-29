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
package me.rei_m.hyakuninisshu.feature.training.ui

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import dagger.Binds
import dagger.multibindings.ClassKey
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.multibindings.IntoMap
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityModule
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.KarutaStyleFilter
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.QuizAnimationSpeed
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.provideViewModel
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.replaceFragment
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.setupActionBar
import me.rei_m.hyakuninisshu.feature.corecomponent.flux.EventObserver
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.ad.AdViewObserver
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.dialog.AlertDialogFragment
import me.rei_m.hyakuninisshu.feature.quiz.ui.QuizAnswerFragment
import me.rei_m.hyakuninisshu.feature.quiz.ui.QuizFragment
import me.rei_m.hyakuninisshu.feature.quiz.ui.QuizInteractionListener
import me.rei_m.hyakuninisshu.feature.training.R
import me.rei_m.hyakuninisshu.feature.training.databinding.ActivityTrainingExamBinding
import javax.inject.Inject

class TrainingExamActivity : DaggerAppCompatActivity(),
    AlertDialogFragment.OnDialogInteractionListener,
    QuizInteractionListener {

    @Inject
    lateinit var viewModelFactory: TrainingViewModel.Factory

    @Inject
    lateinit var adViewObserver: AdViewObserver

    private lateinit var viewModel: TrainingViewModel

    private lateinit var binding: ActivityTrainingExamBinding

    override val kamiNoKuStyle = KarutaStyleFilter.KANJI

    override val shimoNoKuStyle = KarutaStyleFilter.KANA

    override val animationSpeed = QuizAnimationSpeed.NORMAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel(TrainingViewModel::class.java, viewModelFactory)
        viewModel.isVisibleAd.observe(this@TrainingExamActivity, Observer {
            if (it == true) {
                adViewObserver.showAd()
            } else {
                adViewObserver.hideAd()
            }
        })
        viewModel.currentKarutaQuizId.observe(this@TrainingExamActivity, Observer {
            it ?: return@Observer
            onReceiveKarutaQuizId(it)
            supportFragmentManager.findFragmentByTag(TrainingResultFragment.TAG)?.let { _ ->
                replaceFragment(
                    R.id.content,
                    QuizFragment.newInstance(it, kamiNoKuStyle, shimoNoKuStyle, animationSpeed),
                    QuizFragment.TAG,
                    FragmentTransaction.TRANSIT_FRAGMENT_CLOSE
                )
            }
        })
        viewModel.unhandledErrorEvent.observe(this@TrainingExamActivity, EventObserver {
            onErrorQuiz()
        })

        binding = DataBindingUtil.setContentView(this, R.layout.activity_training_exam)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupActionBar(binding.toolbar) {
        }
        setupAd()

        if (supportFragmentManager.fragments.isEmpty()) {
            viewModel.startTraining()
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

    @ActivityScope
    @dagger.Subcomponent(
        modules = [
            ActivityModule::class,
            QuizAnswerFragment.Module::class,
            QuizFragment.Module::class,
            TrainingResultFragment.Module::class
        ]
    )
    interface Subcomponent : AndroidInjector<TrainingExamActivity> {
        @dagger.Subcomponent.Builder
        abstract class Builder : AndroidInjector.Factory<TrainingExamActivity> {
            override fun create(instance: TrainingExamActivity): AndroidInjector<TrainingExamActivity> =
                activityModule(ActivityModule(instance)).build()

            abstract fun activityModule(module: ActivityModule): Builder

            abstract fun build(): AndroidInjector<TrainingExamActivity>
        }
    }

    @dagger.Module(subcomponents = [Subcomponent::class])
    abstract class Module {
        @Binds
        @IntoMap
        @ClassKey(TrainingExamActivity::class)
        abstract fun bind(builder: Subcomponent.Builder): AndroidInjector.Factory<*>
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, TrainingExamActivity::class.java)
    }
}
