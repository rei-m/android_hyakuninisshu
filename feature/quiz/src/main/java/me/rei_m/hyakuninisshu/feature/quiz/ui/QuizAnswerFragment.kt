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
package me.rei_m.hyakuninisshu.feature.quiz.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.Binds
import dagger.android.AndroidInjector
import dagger.android.support.DaggerFragment
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.feature.corecomponent.di.FragmentScope
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.provideFragmentViewModel
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.withArgs
import me.rei_m.hyakuninisshu.feature.corecomponent.flux.EventObserver
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import me.rei_m.hyakuninisshu.feature.quiz.databinding.FragmentQuizAnswerBinding
import me.rei_m.hyakuninisshu.feature.quiz.di.QuizAnswerModule
import me.rei_m.hyakuninisshu.feature.quiz.helper.Navigator
import javax.inject.Inject

class QuizAnswerFragment : DaggerFragment() {

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    @Inject
    lateinit var navigator: Navigator

    @Inject
    lateinit var viewModelFactory: QuizAnswerViewModel.Factory

    private var listener: QuizInteractionListener? = null

    val karutaQuizId by lazy {
        requireNotNull(arguments?.getParcelable<KarutaQuizIdentifier>(ARG_KARUTA_QUIZ_ID)) {
            "$ARG_KARUTA_QUIZ_ID is missing"
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val quizAnswerViewModel = provideFragmentViewModel(QuizAnswerViewModel::class.java, viewModelFactory)

        val binding = FragmentQuizAnswerBinding.inflate(inflater, container, false).apply {
            viewModel = quizAnswerViewModel
            setLifecycleOwner(this@QuizAnswerFragment.viewLifecycleOwner)
        }
        binding.textMaterial.setOnClickListener {
            quizAnswerViewModel.karuta.value?.let {
                navigator.navigateToKaruta(it.identifier)
            }
        }

        quizAnswerViewModel.openNextQuizEvent.observe(this,
            EventObserver {
                listener?.onGoToNext()
            })
        quizAnswerViewModel.openResultEvent.observe(this,
            EventObserver {
                listener?.onGoToResult()
            })
        quizAnswerViewModel.unhandledErrorEvent.observe(this,
            EventObserver {
                listener?.onErrorQuiz()
            })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyticsHelper.sendScreenView("QuizAnswer", requireActivity())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is QuizInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement QuizInteractionListener")
        }
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    @FragmentScope
    @dagger.Subcomponent(
        modules = [
            QuizAnswerModule::class
        ]
    )
    interface Subcomponent : AndroidInjector<QuizAnswerFragment> {
        @dagger.Subcomponent.Builder
        abstract class Builder : AndroidInjector.Builder<QuizAnswerFragment>() {

            abstract fun quizAnswerModule(module: QuizAnswerModule): Builder

            override fun seedInstance(instance: QuizAnswerFragment) {
                quizAnswerModule(QuizAnswerModule(instance.karutaQuizId))
            }
        }
    }

    @dagger.Module(subcomponents = [Subcomponent::class])
    abstract class Module {
        @Binds
        @IntoMap
        @FragmentKey(QuizAnswerFragment::class)
        abstract fun bind(builder: Subcomponent.Builder): AndroidInjector.Factory<out Fragment>
    }

    companion object {

        const val TAG: String = "QuizAnswerFragment"

        private const val ARG_KARUTA_QUIZ_ID = "karutaQuizId"

        fun newInstance(karutaQuizId: KarutaQuizIdentifier) = QuizAnswerFragment().withArgs {
            putParcelable(ARG_KARUTA_QUIZ_ID, karutaQuizId)
        }
    }
}
