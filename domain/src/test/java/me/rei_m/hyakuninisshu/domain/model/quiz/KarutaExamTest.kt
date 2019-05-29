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
package me.rei_m.hyakuninisshu.domain.model.quiz

import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import java.util.*

class KarutaExamTest {

    private lateinit var karutaExam: KarutaExam

    private lateinit var karutaExamResult: KarutaExamResult

    @Before
    fun setUp() {
        karutaExamResult = KarutaExamResult(
            KarutaQuizzesResultSummary(
                quizCount = 100,
                correctCount = 100 - 3,
                averageAnswerSec = 3.4f
            ),
            KarutaIds(
                listOf(
                    KarutaIdentifier(1),
                    KarutaIdentifier(2),
                    KarutaIdentifier(3)
                )
            )
        )

        karutaExam = KarutaExam(KarutaExamIdentifier(1), Date(), karutaExamResult)
    }

    @Test
    fun createInstance() {
        assertThat(karutaExam.result).isEqualTo(karutaExamResult)
    }
}
