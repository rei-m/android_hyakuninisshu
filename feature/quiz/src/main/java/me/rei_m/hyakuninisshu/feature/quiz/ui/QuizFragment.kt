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
import android.view.animation.AlphaAnimation
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dagger.Binds
import dagger.android.AndroidInjector
import dagger.android.support.DaggerFragment
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import me.rei_m.hyakuninisshu.databinding.FragmentQuizBinding
import me.rei_m.hyakuninisshu.feature.corecomponent.di.FragmentScope
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuiz
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizContent
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.ext.withArgs
import me.rei_m.hyakuninisshu.presentation.core.di.QuizFragmentModule
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.KarutaStyleFilter
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.QuizAnimationSpeed
import me.rei_m.hyakuninisshu.presentation.widget.view.KarutaTextView
import me.rei_m.hyakuninisshu.feature.corecomponent.helper.AnalyticsHelper
import me.rei_m.hyakuninisshu.feature.corecomponent.flux.EventObserver
import java.util.Arrays
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class QuizFragment : DaggerFragment() {

    @Inject
    lateinit var analyticsHelper: AnalyticsHelper

    @Inject
    lateinit var viewModelFactory: QuizViewModel.Factory

    private lateinit var quizViewModel: QuizViewModel

    private lateinit var binding: FragmentQuizBinding

    private var listener: CoreInteractionListener? = null

    private val karutaQuizId by lazy {
        requireNotNull(arguments?.getParcelable<KarutaQuizIdentifier>(ARG_KARUTA_QUIZ_ID)) {
            "$ARG_KARUTA_QUIZ_ID is missing"
        }
    }

    private val kamiNoKuStyle by lazy {
        requireNotNull(arguments?.getInt(ARG_KAMI_NO_KU_STYLE)?.let { KarutaStyleFilter[it] }) {
            "$ARG_KAMI_NO_KU_STYLE is missing"
        }
    }

    private val shimoNoKuStyle by lazy {
        requireNotNull(arguments?.getInt(ARG_SHIMO_NO_KU_STYLE)?.let { KarutaStyleFilter[it] }) {
            "$ARG_SHIMO_NO_KU_STYLE is missing"
        }
    }

    private val animationSpeed by lazy {
        requireNotNull(arguments?.getInt(ARG_ANIMATION_SPEED)?.let { QuizAnimationSpeed[it] }) {
            "$ARG_ANIMATION_SPEED is missing"
        }
    }

    private var animationDisposable: Disposable? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        quizViewModel = ViewModelProviders.of(this, viewModelFactory).get(QuizViewModel::class.java)

        binding = FragmentQuizBinding.inflate(inflater, container, false).apply {
            viewModel = quizViewModel
            setLifecycleOwner(this@QuizFragment.viewLifecycleOwner)
        }

        quizViewModel.content.observe(this, Observer<KarutaQuizContent> {
            it ?: return@Observer
            when (it.quiz.state) {
                KarutaQuiz.State.IN_ANSWER -> {
                    val (firstPhrase, secondPhrase, thirdPhrase) = it.yomiFuda(kamiNoKuStyle.value)
                    startDisplayQuizAnimation(firstPhrase, secondPhrase, thirdPhrase)
                }
                KarutaQuiz.State.ANSWERED -> {
                    stopDisplayQuizAnimation()
                }
                else -> {
                }
            }
        })
        quizViewModel.openAnswerEvent.observe(this, EventObserver {
            listener?.onAnswered(it)
        })
        quizViewModel.unhandledErrorEvent.observe(this,
            EventObserver {
                listener?.onErrorQuiz()
            })

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        analyticsHelper.sendScreenView("Quiz", requireActivity())
    }

    override fun onResume() {
        super.onResume()
        quizViewModel.start()
    }

    override fun onPause() {
        stopDisplayQuizAnimation()
        super.onPause()
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

    // TODO: DataBindingに寄せることができるはず.
    private fun startDisplayQuizAnimation(
        firstPhrase: String,
        secondPhrase: String,
        thirdPhrase: String
    ) {
        if (animationDisposable != null) {
            return
        }

        val animationSpeedValue = animationSpeed.value ?: return

        val firstLine = Arrays.asList<KarutaTextView>(
            binding.phrase11,
            binding.phrase12,
            binding.phrase13,
            binding.phrase14,
            binding.phrase15,
            binding.phrase16
        )
        firstLine.forEach { it.visibility = View.INVISIBLE }

        val secondLine = Arrays.asList<KarutaTextView>(
            binding.phrase21,
            binding.phrase22,
            binding.phrase23,
            binding.phrase24,
            binding.phrase25,
            binding.phrase26,
            binding.phrase27,
            binding.phrase28
        )
        secondLine.forEach { it.visibility = View.INVISIBLE }

        val thirdLine = Arrays.asList<KarutaTextView>(
            binding.phrase31,
            binding.phrase32,
            binding.phrase33,
            binding.phrase34,
            binding.phrase35,
            binding.phrase36
        )

        thirdLine.forEach { it.visibility = View.INVISIBLE }

        val totalKarutaTextViewList = ArrayList<TextView>().apply {
            addAll(firstLine.take(firstPhrase.length))
            addAll(secondLine.take(secondPhrase.length))
            addAll(thirdLine.take(thirdPhrase.length))
        }

        animationDisposable = Observable.interval(animationSpeedValue, TimeUnit.MILLISECONDS)
            .zipWith(totalKarutaTextViewList) { _, textView -> textView }
            .observeOn(AndroidSchedulers.mainThread())
            .doOnDispose {
                totalKarutaTextViewList.forEach { it.animation?.cancel() }
            }
            .subscribe { textView ->
                val fadeIn = AlphaAnimation(0f, 1f).apply {
                    interpolator = DecelerateInterpolator()
                    duration = animationSpeedValue
                }
                textView.visibility = View.VISIBLE
                textView.startAnimation(fadeIn)
            }
    }

    private fun stopDisplayQuizAnimation() {
        animationDisposable?.dispose()
        animationDisposable = null
    }

    @FragmentScope
    @dagger.Subcomponent(
        modules = [
            QuizFragmentModule::class
        ]
    )
    interface Subcomponent : AndroidInjector<QuizFragment> {
        @dagger.Subcomponent.Builder
        abstract class Builder : AndroidInjector.Builder<QuizFragment>() {

            abstract fun quizFragmentModule(module: QuizFragmentModule): Builder

            override fun seedInstance(instance: QuizFragment) {
                quizFragmentModule(
                    QuizFragmentModule(
                        instance.karutaQuizId,
                        instance.kamiNoKuStyle,
                        instance.shimoNoKuStyle
                    )
                )
            }
        }
    }

    @dagger.Module(subcomponents = [Subcomponent::class])
    abstract class Module {
        @Binds
        @IntoMap
        @FragmentKey(QuizFragment::class)
        abstract fun bind(builder: Subcomponent.Builder): AndroidInjector.Factory<out Fragment>
    }

    companion object {

        const val TAG: String = "QuizFragment"

        private const val ARG_KARUTA_QUIZ_ID = "karutaQuizId"

        private const val ARG_KAMI_NO_KU_STYLE = "kamiNoKuStyle"

        private const val ARG_SHIMO_NO_KU_STYLE = "shimoNoKuStyle"

        private const val ARG_ANIMATION_SPEED = "animationSpeed"

        fun newInstance(
            karutaQuizId: KarutaQuizIdentifier,
            kamiNoKuStyle: KarutaStyleFilter,
            shimoNoKuStyle: KarutaStyleFilter,
            animationSpeed: QuizAnimationSpeed
        ) = QuizFragment().withArgs {
            putParcelable(ARG_KARUTA_QUIZ_ID, karutaQuizId)
            putInt(ARG_KAMI_NO_KU_STYLE, kamiNoKuStyle.ordinal)
            putInt(ARG_SHIMO_NO_KU_STYLE, shimoNoKuStyle.ordinal)
            putInt(ARG_ANIMATION_SPEED, animationSpeed.ordinal)
        }
    }
}
