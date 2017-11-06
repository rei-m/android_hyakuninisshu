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
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class KarutaExamResultTest {

    @Mock
    private KarutaQuizzesResultSummary resultSummary;

    private KarutaIds wrongKarutaIds;

    private KarutaExamResult karutaExamResult;

    @Before
    public void setUp() throws Exception {
        wrongKarutaIds = new KarutaIds(new ArrayList<KarutaIdentifier>() {{
            add(new KarutaIdentifier(1));
            add(new KarutaIdentifier(2));
            add(new KarutaIdentifier(3));
            add(new KarutaIdentifier(4));
            add(new KarutaIdentifier(5));
        }});
        karutaExamResult = new KarutaExamResult(resultSummary, wrongKarutaIds);
    }

    @Test
    public void quizCount() throws Exception {
        when(resultSummary.quizCount()).thenReturn(10);
        assertThat(karutaExamResult.quizCount(), is(10));
    }

    @Test
    public void score() throws Exception {
        when(resultSummary.score()).thenReturn("10/100");
        assertThat(karutaExamResult.score(), is("10/100"));
    }

    @Test
    public void averageAnswerTime() throws Exception {
        when(resultSummary.averageAnswerTime()).thenReturn(5.0f);
        assertThat(karutaExamResult.averageAnswerTime(), is(5.0f));
    }

    @Test
    public void wrongKarutaIds() throws Exception {
        assertThat(karutaExamResult.wrongKarutaIds(), is(wrongKarutaIds));
    }

    @Test
    public void judgements() throws Exception {
        assertThat(karutaExamResult.judgements().size(), is(100));
        assertThat(karutaExamResult.judgements().get(0), is(new KarutaQuizJudgement(wrongKarutaIds.asList().get(0), false)));
        assertThat(karutaExamResult.judgements().get(5), is(new KarutaQuizJudgement(new KarutaIdentifier(6), true)));
    }
}
