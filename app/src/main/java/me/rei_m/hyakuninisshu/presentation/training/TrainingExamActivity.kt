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
import me.rei_m.hyakuninisshu.databinding.ActivityTrainingExamBinding
import me.rei_m.hyakuninisshu.di.ForActivity
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.ext.AppCompatActivityExt
import me.rei_m.hyakuninisshu.presentation.widget.dialog.AlertDialogFragment
import me.rei_m.hyakuninisshu.presentation.widget.ad.AdViewObserver
import me.rei_m.hyakuninisshu.presentation.core.CoreInteractionListener
import me.rei_m.hyakuninisshu.presentation.core.QuizAnswerFragment
import me.rei_m.hyakuninisshu.presentation.core.QuizFragment
import me.rei_m.hyakuninisshu.presentation.di.ActivityModule
import me.rei_m.hyakuninisshu.presentation.enums.KarutaStyleFilter
import javax.inject.Inject

class TrainingExamActivity : DaggerAppCompatActivity(),
        AlertDialogFragment.OnDialogInteractionListener,
        CoreInteractionListener,
        AppCompatActivityExt {

    @Inject
    lateinit var storeFactory: TrainingStore.Factory

    @Inject
    lateinit var viewModelFactory: TrainingViewModel.Factory

    @Inject
    lateinit var adViewObserver: AdViewObserver

    private lateinit var viewModel: TrainingViewModel

    private lateinit var binding: ActivityTrainingExamBinding

    private val kamiNoKuStyle = KarutaStyleFilter.KANJI

    private val shimoNoKuStyle = KarutaStyleFilter.KANA

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = viewModelFactory.create(obtainStore(TrainingStore::class.java, storeFactory))
        with(viewModel) {
            isVisibleAd.observe(this@TrainingExamActivity, Observer {
                if (it == true) {
                    adViewObserver.showAd()
                } else {
                    adViewObserver.hideAd()
                }
            })
            currentKarutaQuizId.observe(this@TrainingExamActivity, Observer {
                it ?: return@Observer
                if (supportFragmentManager.fragments.isEmpty()) {
                    supportFragmentManager
                            .beginTransaction()
                            .add(R.id.content, QuizFragment.newInstance(it, kamiNoKuStyle, shimoNoKuStyle), QuizFragment.TAG)
                            .commit()
                    return@Observer
                }

                supportFragmentManager.findFragmentByTag(QuizAnswerFragment.TAG)?.let { fragment ->
                    if ((fragment as QuizAnswerFragment).karutaQuizId != it) {
                        supportFragmentManager
                                .beginTransaction()
                                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                                .replace(R.id.content, QuizFragment.newInstance(it, kamiNoKuStyle, shimoNoKuStyle), QuizFragment.TAG)
                                .commit()
                        return@Observer
                    }
                }

                supportFragmentManager.findFragmentByTag(TrainingResultFragment.TAG)?.let { _ ->
                    supportFragmentManager
                            .beginTransaction()
                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                            .replace(R.id.content, QuizFragment.newInstance(it, kamiNoKuStyle, shimoNoKuStyle), QuizFragment.TAG)
                            .commit()
                    return@Observer
                }
            })
        }

        binding = DataBindingUtil.setContentView(this, R.layout.activity_training_exam)
        binding.setLifecycleOwner(this)

        setupActionBar(binding.toolbar) {
        }
        setupAd()

        binding.viewModel = viewModel

        if (supportFragmentManager.fragments.isEmpty()) {
            viewModel.startTraining()
        }
    }

//    override fun onErrorQuiz() {
//        val newFragment = AlertDialogFragment.newInstance(
//                R.string.text_title_error,
//                R.string.text_message_quiz_error,
//                true,
//                false)
//        newFragment.show(supportFragmentManager, AlertDialogFragment.TAG)
//    }

    override fun onAnswered(quizId: KarutaQuizIdentifier) {
        if (supportFragmentManager.findFragmentByTag(QuizAnswerFragment.TAG) == null) {
            supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.content, QuizAnswerFragment.newInstance(quizId), QuizAnswerFragment.TAG)
                    .commit()
        }
    }

    override fun onGoToNext() {
        viewModel.fetchNext()
    }

    override fun onGoToResult() {
        if (supportFragmentManager.findFragmentByTag(TrainingResultFragment.TAG) == null) {
            supportFragmentManager
                    .beginTransaction()
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .replace(R.id.content, TrainingResultFragment.newInstance(), TrainingResultFragment.TAG)
                    .commit()
        }
    }

    override fun onDialogPositiveClick() {
        finish()
    }

    override fun onDialogNegativeClick() {

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
        QuizAnswerFragment.Module::class,
        QuizFragment.Module::class,
        TrainingResultFragment.Module::class
    ])
    interface Subcomponent : AndroidInjector<TrainingExamActivity> {
        @dagger.Subcomponent.Builder
        abstract class Builder : AndroidInjector.Builder<TrainingExamActivity>() {

            abstract fun activityModule(module: ActivityModule): Builder

            override fun seedInstance(instance: TrainingExamActivity) {
                activityModule(ActivityModule(instance))
            }
        }
    }

    @dagger.Module(subcomponents = [Subcomponent::class])
    abstract class Module {
        @Binds
        @IntoMap
        @ActivityKey(TrainingExamActivity::class)
        abstract fun bind(builder: Subcomponent.Builder): AndroidInjector.Factory<out Activity>
    }

    companion object {
        fun createIntent(context: Context) = Intent(context, TrainingExamActivity::class.java)
    }
}
