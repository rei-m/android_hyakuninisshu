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
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

class KarutaQuizResultTest {

    private lateinit var karutaQuizResult: KarutaQuizResult

    private lateinit var correctKarutaId: KarutaIdentifier

    @Before
    fun setUp() {
        correctKarutaId = KarutaIdentifier(1)
        karutaQuizResult = KarutaQuizResult(correctKarutaId, ChoiceNo.FIRST, true, 5000)
    }

    @Test
    fun createInstance() {
        assertThat(karutaQuizResult.judgement).isEqualTo(KarutaQuizJudgement(correctKarutaId, true))
        assertThat(karutaQuizResult.choiceNo).isEqualTo(ChoiceNo.FIRST)
        assertThat(karutaQuizResult.answerMillSec).isEqualTo(5000L)
    }
}
