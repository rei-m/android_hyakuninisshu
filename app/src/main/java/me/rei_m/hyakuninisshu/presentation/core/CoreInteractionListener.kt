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

import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import me.rei_m.hyakuninisshu.R
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.ext.addFragment
import me.rei_m.hyakuninisshu.ext.replaceFragment
import me.rei_m.hyakuninisshu.ext.showAlertDialog
import me.rei_m.hyakuninisshu.presentation.enums.KarutaStyleFilter
import me.rei_m.hyakuninisshu.presentation.training.TrainingResultFragment

interface CoreInteractionListener {

    val kamiNoKuStyle: KarutaStyleFilter

    val shimoNoKuStyle: KarutaStyleFilter

    fun onAnswered(quizId: KarutaQuizIdentifier)

    fun onGoToNext()

    fun onGoToResult()

    fun onErrorQuiz()

    fun AppCompatActivity.onReceiveKarutaQuizId(karutaQuizId: KarutaQuizIdentifier) {
        if (supportFragmentManager.fragments.isEmpty()) {
            addFragment(
                R.id.content,
                QuizFragment.newInstance(karutaQuizId, kamiNoKuStyle, shimoNoKuStyle),
                QuizFragment.TAG
            )
            return
        }

        supportFragmentManager.findFragmentByTag(QuizAnswerFragment.TAG)?.let { fragment ->
            if (fragment is QuizAnswerFragment && fragment.karutaQuizId != karutaQuizId) {
                replaceFragment(
                    R.id.content,
                    QuizFragment.newInstance(karutaQuizId, kamiNoKuStyle, shimoNoKuStyle),
                    QuizFragment.TAG,
                    FragmentTransaction.TRANSIT_FRAGMENT_CLOSE
                )
            }
            return
        }

        supportFragmentManager.findFragmentByTag(TrainingResultFragment.TAG)?.let { _ ->
            replaceFragment(
                R.id.content,
                QuizFragment.newInstance(karutaQuizId, kamiNoKuStyle, shimoNoKuStyle),
                QuizFragment.TAG,
                FragmentTransaction.TRANSIT_FRAGMENT_CLOSE
            )
            return
        }
    }

    fun AppCompatActivity.openAnswer(quizId: KarutaQuizIdentifier) {
        if (supportFragmentManager.findFragmentByTag(QuizAnswerFragment.TAG) == null) {
            replaceFragment(
                R.id.content,
                QuizAnswerFragment.newInstance(quizId),
                QuizAnswerFragment.TAG,
                FragmentTransaction.TRANSIT_FRAGMENT_FADE
            )
        }
    }

    fun AppCompatActivity.showError() {
        showAlertDialog(R.string.text_title_error, R.string.text_message_quiz_error)
    }
}
