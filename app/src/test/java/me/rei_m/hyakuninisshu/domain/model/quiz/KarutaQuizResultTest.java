/*
 * Copyright (c) 2017. Rei Matsushita
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

package me.rei_m.hyakuninisshu.domain.model.quiz;

import org.junit.Before;
import org.junit.Test;

import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class KarutaQuizResultTest {

    private KarutaQuizResult karutaQuizResult;

    private KarutaIdentifier correctKarutaId;

    @Before
    public void setUp() throws Exception {
        correctKarutaId = new KarutaIdentifier(1);
        karutaQuizResult = new KarutaQuizResult(correctKarutaId, ChoiceNo.FIRST, true, 5000);
    }

    @Test
    public void judgement() throws Exception {
        assertThat(karutaQuizResult.judgement(), is(new KarutaQuizJudgement(correctKarutaId, true)));
    }

    @Test
    public void choiceNo() throws Exception {
        assertThat(karutaQuizResult.choiceNo(), is(ChoiceNo.FIRST));
    }

    @Test
    public void answerTime() throws Exception {
        assertThat(karutaQuizResult.answerMillSec(), is(5000L));
    }
}
