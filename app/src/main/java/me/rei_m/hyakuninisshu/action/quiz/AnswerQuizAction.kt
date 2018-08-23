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
package me.rei_m.hyakuninisshu.action.quiz

import me.rei_m.hyakuninisshu.action.Action
import me.rei_m.hyakuninisshu.domain.model.quiz.KarutaQuizContent

class AnswerQuizAction private constructor(
    val karutaQuizContent: KarutaQuizContent?,
    override val error: Exception? = null
) : Action {

    override val name = "AnswerQuizAction"

    override fun toString() = if (isSucceeded) "$name(karutaQuizContent=$karutaQuizContent)" else "$name(error=$error)"

    companion object {
        fun createSuccess(karutaQuizContent: KarutaQuizContent) = AnswerQuizAction(karutaQuizContent)
        fun createError(error: Exception) = AnswerQuizAction(null, error)
    }
}
