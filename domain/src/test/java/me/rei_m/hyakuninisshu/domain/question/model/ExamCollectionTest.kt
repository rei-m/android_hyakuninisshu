/*
 * Copyright (c) 2025. Rei Matsushita
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

package me.rei_m.hyakuninisshu.domain.question.model

import me.rei_m.hyakuninisshu.domain.helper.TestHelper
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNo
import me.rei_m.hyakuninisshu.domain.karuta.model.KarutaNoCollection
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.experimental.runners.Enclosed
import org.junit.runner.RunWith

@RunWith(Enclosed::class)
class ExamCollectionTest : TestHelper {
    @Test
    fun all() {
        val list = listOf(createExam(), createExam(), createExam())
        val actual = ExamCollection(list).all
        assertThat(actual).isEqualTo(list)
    }

    class WhenNotOverflowed : TestHelper {
        @Test
        fun overflowed() {
            val list = listOf(createExam(), createExam(), createExam())
            val actual = ExamCollection(list).overflowed
            assertThat(actual).isEqualTo(listOf<Exam>())
        }
    }

    class WhenOverflowed : TestHelper {
        @Test
        fun overflowed() {
            val list = mutableListOf<Exam>()
            repeat(11) {
                list.add(createExam())
            }
            val actual = ExamCollection(list).overflowed
            assertThat(actual).isEqualTo(listOf(list.last()))
        }
    }

    class WhenExamNotHasWrong : TestHelper {
        @Test
        fun totalWrongKarutaNoCollection() {
            val list =
                listOf(
                    createExam(
                        wrongKarutaNoCollection = KarutaNoCollection(listOf()),
                    ),
                )
            val actual = ExamCollection(list).totalWrongKarutaNoCollection
            assertThat(actual).isEqualTo(KarutaNoCollection(listOf()))
        }
    }

    class WhenExamHasWrong : TestHelper {
        @Test
        fun totalWrongKarutaNoCollection() {
            val list =
                listOf(
                    createExam(
                        wrongKarutaNoCollection =
                            KarutaNoCollection(
                                listOf(
                                    KarutaNo(1),
                                ),
                            ),
                    ),
                    createExam(
                        wrongKarutaNoCollection =
                            KarutaNoCollection(
                                listOf(
                                    KarutaNo(2),
                                ),
                            ),
                    ),
                )
            val actual = ExamCollection(list).totalWrongKarutaNoCollection
            assertThat(actual).isEqualTo(KarutaNoCollection(listOf(KarutaNo(1), KarutaNo(2))))
        }
    }
}
