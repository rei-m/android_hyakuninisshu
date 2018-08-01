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
import android.view.animation.AlphaAnimation
import android.view.animation.DecelerateInterpolator
import android.widget.TextView
import dagger.android.ContributesAndroidInjector
import dagger.android.support.DaggerFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import me.rei_m.hyakuninisshu.databinding.FragmentQuizBinding
import me.rei_m.hyakuninisshu.di.ForFragment
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuiz
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.ext.FragmentExt
import me.rei_m.hyakuninisshu.presentation.enums.KarutaStyleFilter
import me.rei_m.hyakuninisshu.presentation.widget.view.KarutaTextView
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class QuizFragment : DaggerFragment(), FragmentExt {

    @Inject
    lateinit var storeFactory: QuizStore.Factory

    @Inject
    lateinit var viewModelFactory: QuizViewModel.Factory

    private lateinit var viewModel: QuizViewModel

    private lateinit var binding: FragmentQuizBinding

    private var listener: CoreInteractionListener? = null

    private val karutaQuizId: KarutaQuizIdentifier
        get() = requireNotNull(arguments?.getParcelable(ARG_KARUTA_QUIZ_ID)) {
            "$ARG_KARUTA_QUIZ_ID is missing"
        }

    private val kamiNoKuStyle: KarutaStyleFilter
        get() = requireNotNull(arguments?.getInt(ARG_KAMI_NO_KU_STYLE)?.let { KarutaStyleFilter[it] }) {
            "$ARG_KAMI_NO_KU_STYLE is missing"
        }

    private val shimoNoKuStyle: KarutaStyleFilter
        get() = requireNotNull(arguments?.getInt(ARG_SHIMO_NO_KU_STYLE)?.let { KarutaStyleFilter[it] }) {
            "$ARG_SHIMO_NO_KU_STYLE is missing"
        }

    private var animationDisposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val store = obtainFragmentStore(QuizStore::class.java, storeFactory)
        viewModel = viewModelFactory.create(store, karutaQuizId, kamiNoKuStyle, shimoNoKuStyle).apply {
            content.observe(this@QuizFragment, Observer {
                it ?: let { return@Observer }
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
            openAnswerEvent.observe(this@QuizFragment, Observer {
                it ?: let { return@Observer }
                listener?.onAnswered(it)
            })
            unhandledErrorEvent.observe(this@QuizFragment, Observer {
                listener?.onErrorQuiz()
            })
        }

        binding = FragmentQuizBinding.inflate(inflater, container, false).apply {
            setLifecycleOwner(this@QuizFragment)
        }

        binding.viewModel = viewModel

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.start()
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
    private fun startDisplayQuizAnimation(firstPhrase: String,
                                          secondPhrase: String,
                                          thirdPhrase: String) {
        if (animationDisposable != null) {
            return
        }

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

        animationDisposable = Observable.interval(SPEED_DISPLAY_ANIMATION_MILL_SEC, TimeUnit.MILLISECONDS)
                .zipWith(totalKarutaTextViewList) { _, textView -> textView }
                .observeOn(AndroidSchedulers.mainThread())
                .doOnDispose {
                    totalKarutaTextViewList.forEach { it.animation?.cancel() }
                }
                .subscribe { textView ->
                    val fadeIn = AlphaAnimation(0f, 1f).apply {
                        interpolator = DecelerateInterpolator()
                        duration = SPEED_DISPLAY_ANIMATION_MILL_SEC
                    }
                    textView.visibility = View.VISIBLE
                    textView.startAnimation(fadeIn)
                }
    }

    private fun stopDisplayQuizAnimation() {
        animationDisposable?.dispose()
        animationDisposable = null
    }

    @dagger.Module
    abstract class Module {
        @ForFragment
        @ContributesAndroidInjector
        abstract fun contributeInjector(): QuizFragment
    }

    companion object : FragmentExt {

        val TAG: String = QuizFragment::class.java.simpleName

        private const val ARG_KARUTA_QUIZ_ID = "karutaQuizId"

        private const val ARG_KAMI_NO_KU_STYLE = "kamiNoKuStyle"

        private const val ARG_SHIMO_NO_KU_STYLE = "shimoNoKuStyle"

        private const val SPEED_DISPLAY_ANIMATION_MILL_SEC: Long = 500

        fun newInstance(karutaQuizId: KarutaQuizIdentifier,
                        kamiNoKuStyle: KarutaStyleFilter,
                        shimoNoKuStyle: KarutaStyleFilter) = QuizFragment().withArgs {
            putParcelable(ARG_KARUTA_QUIZ_ID, karutaQuizId)
            putInt(ARG_KAMI_NO_KU_STYLE, kamiNoKuStyle.ordinal)
            putInt(ARG_SHIMO_NO_KU_STYLE, shimoNoKuStyle.ordinal)
        }
    }
}
