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
package me.rei_m.hyakuninisshu.presentation.exam

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dagger.Binds
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.android.support.DaggerAppCompatActivity
import dagger.multibindings.IntoMap
import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.databinding.ActivityExamBinding
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityScope
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.ext.replaceFragment
import me.rei_m.hyakuninisshu.ext.setupActionBar
import me.rei_m.hyakuninisshu.ext.showAlertDialog
import me.rei_m.hyakuninisshu.feature.corecomponent.di.ActivityModule
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.ad.AdViewObserver
import me.rei_m.hyakuninisshu.presentation.core.CoreInteractionListener
import me.rei_m.hyakuninisshu.presentation.core.QuizAnswerFragment
import me.rei_m.hyakuninisshu.presentation.core.QuizFragment
import me.rei_m.hyakuninisshu.presentation.di.OldActivityModule
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.KarutaStyleFilter
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.QuizAnimationSpeed
import me.rei_m.hyakuninisshu.feature.corecomponent.widget.dialog.AlertDialogFragment
import me.rei_m.hyakuninisshu.feature.corecomponent.event.EventObserver
import javax.inject.Inject

class ExamActivity : DaggerAppCompatActivity(),
    CoreInteractionListener,
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
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ExamViewModel::class.java)
        with(viewModel) {
            isVisibleAd.observe(this@ExamActivity, Observer {
                if (it == true) {
                    adViewObserver.showAd()
                } else {
                    adViewObserver.hideAd()
                }
            })
            currentKarutaQuizId.observe(this@ExamActivity, Observer {
                it ?: return@Observer
                onReceiveKarutaQuizId(it)
            })
            notFoundQuizEvent.observe(this@ExamActivity, EventObserver {
                onErrorQuiz()
            })
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_exam)
        binding.setLifecycleOwner(this)

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
            OldActivityModule::class,
            QuizAnswerFragment.Module::class,
            QuizFragment.Module::class,
            ExamResultFragment.Module::class
        ]
    )
    interface Subcomponent : AndroidInjector<ExamActivity> {
        @dagger.Subcomponent.Builder
        abstract class Builder : AndroidInjector.Builder<ExamActivity>() {

            abstract fun oldActivityModule(module: OldActivityModule): Builder

            abstract fun activityModule(module: ActivityModule): Builder

            override fun seedInstance(instance: ExamActivity) {
                activityModule(ActivityModule(instance))
                oldActivityModule(OldActivityModule(instance))
            }
        }
    }

    @dagger.Module(subcomponents = [Subcomponent::class])
    abstract class Module {
        @Binds
        @IntoMap
        @ActivityKey(ExamActivity::class)
        abstract fun bind(builder: Subcomponent.Builder): AndroidInjector.Factory<out Activity>
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, ExamActivity::class.java)
    }
}
