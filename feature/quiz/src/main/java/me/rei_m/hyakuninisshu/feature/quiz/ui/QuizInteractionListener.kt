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

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizIdentifier
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.KarutaStyleFilter
import me.rei_m.hyakuninisshu.feature.corecomponent.enums.QuizAnimationSpeed
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.addFragment
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.replaceFragment
import me.rei_m.hyakuninisshu.feature.corecomponent.ext.showAlertDialog
import me.rei_m.hyakuninisshu.feature.quiz.R

interface QuizInteractionListener {

    val kamiNoKuStyle: KarutaStyleFilter

    val shimoNoKuStyle: KarutaStyleFilter

    val animationSpeed: QuizAnimationSpeed

    fun onAnswered(quizId: KarutaQuizIdentifier)

    fun onGoToNext()

    fun onGoToResult()

    fun onErrorQuiz()

    fun AppCompatActivity.onReceiveKarutaQuizId(karutaQuizId: KarutaQuizIdentifier) {
        if (supportFragmentManager.fragments.isEmpty()) {
            addFragment(
                R.id.content,
                QuizFragment.newInstance(karutaQuizId, kamiNoKuStyle, shimoNoKuStyle, animationSpeed),
                QuizFragment.TAG
            )
            return
        }

        supportFragmentManager.findFragmentByTag(QuizAnswerFragment.TAG)?.let { fragment ->
            if (fragment is QuizAnswerFragment && fragment.karutaQuizId != karutaQuizId) {
                replaceFragment(
                    R.id.content,
                    QuizFragment.newInstance(karutaQuizId, kamiNoKuStyle, shimoNoKuStyle, animationSpeed),
                    QuizFragment.TAG,
                    FragmentTransaction.TRANSIT_FRAGMENT_CLOSE
                )
            }
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
