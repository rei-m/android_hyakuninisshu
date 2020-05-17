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
package me.rei_m.hyakuninisshu.feature.exam.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import dagger.Binds
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.multibindings.ClassKey
import dagger.multibindings.IntoMap
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityModule
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.KarutaStyleFilter
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.QuizAnimationSpeed
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.provideViewModel
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.replaceFragment
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.setupActionBar
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.showAlertDialog
import me.rei_m.hyakuninisshu.feature.corecomponent.flux.EventObserver
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.ad.AdViewObserver
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.dialog.AlertDialogFragment
import me.rei_m.hyakuninisshu.feature.exam.R
import me.rei_m.hyakuninisshu.feature.exam.databinding.ActivityExamBinding
import me.rei_m.hyakuninisshu.feature.quiz.ui.QuizAnswerFragment
import me.rei_m.hyakuninisshu.feature.quiz.ui.QuizFragment
import me.rei_m.hyakuninisshu.feature.quiz.ui.QuizInteractionListener
import javax.inject.Inject

class ExamActivity : DaggerAppCompatActivity(),
    QuizInteractionListener,
    ExamResultFragment.OnFragmentInteractionListener,
    AlertDialogFragment.OnDialogInteractionListener {

    @Inject
    lateinit var viewModelFactory: ExamViewModel.Factory

    @Inject
    lateinit var adViewObserver: AdViewObserver

    private lateinit var viewModel: ExamViewModel

    private lateinit var binding: ActivityExamBinding

    override val kamiNoKuStyle = KarutaStyleFilter.KANJI

    override val shimoNoKuStyle = KarutaStyleFilter.KANA

    override val animationSpeed = QuizAnimationSpeed.NORMAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = provideViewModel(ExamViewModel::class.java, viewModelFactory)

        viewModel.isVisibleAd.observe(this, Observer {
            if (it == true) {
                adViewObserver.showAd()
            } else {
                adViewObserver.hideAd()
            }
        })
        viewModel.currentKarutaQuizId.observe(this, Observer {
            it ?: return@Observer
            onReceiveKarutaQuizId(it)
        })
        viewModel.notFoundQuizEvent.observe(this, EventObserver {
            onErrorQuiz()
        })

        binding = DataBindingUtil.setContentView(this, R.layout.activity_exam)
        binding.lifecycleOwner = this

        setupActionBar(binding.toolbar) {
        }
        setupAd()

        if (supportFragmentManager.fragments.isEmpty()) {
            viewModel.startExam()
        }
    }

    override fun onAnswered(quizId: KarutaQuizIdentifier) {
        openAnswer(quizId)
    }

    override fun onGoToNext() {
        viewModel.fetchNext()
    }

    override fun onGoToResult() {
        if (supportFragmentManager.findFragmentByTag(ExamResultFragment.TAG) == null) {
            replaceFragment(
                R.id.content,
                ExamResultFragment.newInstance(),
                ExamResultFragment.TAG,
                FragmentTransaction.TRANSIT_FRAGMENT_FADE
            )
        }
    }

    override fun onErrorQuiz() {
        showError()
    }

    override fun onErrorFinish() {
        showAlertDialog(R.string.text_title_error, R.string.text_message_aggregate_error)
    }

    override fun onAlertPositiveClick() {
        finish()
    }

    override fun onAlertNegativeClick() {
        // Negative Button is disable.
    }

    private fun setupAd() {
        lifecycle.addObserver(adViewObserver)
        adViewObserver.loadAd(this, binding.adViewContainer)
        if (viewModel.isVisibleAd.value == true) {
            adViewObserver.showAd()
        } else {
            adViewObserver.hideAd()
        }
    }

    @ActivityScope
    @dagger.Subcomponent(
        modules = [
            ActivityModule::class,
            QuizAnswerFragment.Module::class,
            QuizFragment.Module::class,
            ExamResultFragment.Module::class
        ]
    )
    interface Subcomponent : AndroidInjector<ExamActivity> {
        @dagger.Subcomponent.Builder
        abstract class Builder : AndroidInjector.Factory<ExamActivity> {
            override fun create(instance: ExamActivity): AndroidInjector<ExamActivity> =
                activityModule(ActivityModule(instance)).build()

            abstract fun activityModule(module: ActivityModule): Builder

            abstract fun build(): AndroidInjector<ExamActivity>
        }
    }

    @dagger.Module(subcomponents = [Subcomponent::class])
    abstract class Module {
        @Binds
        @IntoMap
        @ClassKey(ExamActivity::class)
        abstract fun bind(builder: Subcomponent.Builder): AndroidInjector.Factory<*>
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, ExamActivity::class.java)
    }
}
