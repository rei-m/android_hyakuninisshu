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

package me.rei_m.hyakuninisshu.domain.model.quiz

import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class KarutaExamResultTest {

    private lateinit var resultSummary: KarutaQuizzesResultSummary

    private lateinit var wrongKarutaIds: KarutaIds

    private lateinit var karutaExamResult: KarutaExamResult

    @Before
    fun setUp() {
        resultSummary = KarutaQuizzesResultSummary(
                quizCount = 100,
                averageAnswerSec = 5.0f,
                correctCount = 10
        )

        wrongKarutaIds = KarutaIds(arrayListOf(
                KarutaIdentifier(1),
                KarutaIdentifier(2),
                KarutaIdentifier(3),
                KarutaIdentifier(4),
                KarutaIdentifier(5)
        ))
        karutaExamResult = KarutaExamResult(resultSummary, wrongKarutaIds)
    }

    @Test
    fun createInstance() {
        assertThat(karutaExamResult.quizCount).isEqualTo(100)
        assertThat(karutaExamResult.score).isEqualTo("10/100")
        assertThat(karutaExamResult.averageAnswerSec).isEqualTo(5.0f)
        assertThat(karutaExamResult.wrongKarutaIds).isEqualTo(wrongKarutaIds)
        assertThat(karutaExamResult.judgements.size).isEqualTo(100)
        assertThat(karutaExamResult.judgements[0]).isEqualTo(KarutaQuizJudgement(wrongKarutaIds.values[0], false))
        assertThat(karutaExamResult.judgements[5]).isEqualTo(KarutaQuizJudgement(KarutaIdentifier(6), true))
    }
}
