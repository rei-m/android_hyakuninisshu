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

package me.rei_m.hyakuninisshu.presentation.core

import android.arch.lifecycle.Observer
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import me.rei_m.hyakuninisshu.databinding.FragmentQuizAnswerBinding
import me.rei_m.hyakuninisshu.di.ForFragment
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.ext.FragmentExt
import javax.inject.Inject

class QuizAnswerFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: QuizAnswerViewModel.Factory

    private var listener: CoreInteractionListener? = null

    val karutaQuizId by lazy {
        requireNotNull(arguments?.getParcelable<KarutaQuizIdentifier>(ARG_KARUTA_QUIZ_ID)) {
            "$ARG_KARUTA_QUIZ_ID is missing"
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val viewModel = viewModelFactory.create(this, karutaQuizId).apply {
            openNextQuizEvent.observe(this@QuizAnswerFragment, Observer {
                listener?.onGoToNext()
            })
            openResultEvent.observe(this@QuizAnswerFragment, Observer {
                listener?.onGoToResult()
            })
            unhandledErrorEvent.observe(this@QuizAnswerFragment, Observer {
                listener?.onErrorQuiz()
            })
        }

        val binding = FragmentQuizAnswerBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@QuizAnswerFragment)
        }

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is CoreInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement CoreInteractionListener")
        }
    }

    override fun onDetach() {
        listener = null
        super.onDetach()
    }

    @dagger.Module
    abstract class Module {
        @ForFragment
        @ContributesAndroidInjector
        abstract fun contributeInjector(): QuizAnswerFragment
    }

    companion object : FragmentExt {

        const val TAG: String = "QuizAnswerFragment"

        private const val ARG_KARUTA_QUIZ_ID = "karutaQuizId"

        fun newInstance(karutaQuizId: KarutaQuizIdentifier) = QuizAnswerFragment().withArgs {
            putParcelable(ARG_KARUTA_QUIZ_ID, karutaQuizId)
        }
    }
}
