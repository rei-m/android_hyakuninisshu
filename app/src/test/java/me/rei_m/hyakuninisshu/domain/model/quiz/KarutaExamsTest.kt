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
import java.util.*

class KarutaExamsTest {
    class WhenNotEmpty {

        private lateinit var karutaExams: KarutaExams

        private lateinit var values: List<KarutaExam>

        @Before
        fun setUp() {
            values = arrayListOf(
                    createExam(createKarutaIds(listOf(1))),
                    createExam(createKarutaIds(listOf(1, 2, 3))),
                    createExam(createKarutaIds(listOf()))
            )
            karutaExams = KarutaExams(values)
        }

        @Test
        fun createInstance() {
            assertThat(karutaExams.recent).isEqualTo(values.first())
            assertThat(karutaExams.totalWrongKarutaIds).isEqualTo(createKarutaIds(listOf(1, 2, 3)))
        }
    }

    class WhenEmpty {

        private lateinit var karutaExams: KarutaExams

        private lateinit var values: List<KarutaExam>

        @Before
        fun setUp() {
            values = ArrayList()
            karutaExams = KarutaExams(values)
        }

        @Test
        fun createInstance() {
            assertThat(karutaExams.recent).isNull()
            assertThat(karutaExams.totalWrongKarutaIds).isEqualTo(createKarutaIds(listOf()))
        }
    }

    companion object {
        private fun createKarutaIds(ids: List<Int>): KarutaIds = KarutaIds(ids.map { KarutaIdentifier(it) })

        private fun createExam(wrongKarutaIds: KarutaIds): KarutaExam {

            val karutaExamResult = KarutaExamResult(
                    KarutaQuizzesResultSummary(
                            quizCount = 100,
                            correctCount = 100 - wrongKarutaIds.size,
                            averageAnswerSec = 3.4f
                    ),
                    wrongKarutaIds
            )

            return KarutaExam(KarutaExamIdentifier(1), Date(), karutaExamResult)
        }
    }
}
