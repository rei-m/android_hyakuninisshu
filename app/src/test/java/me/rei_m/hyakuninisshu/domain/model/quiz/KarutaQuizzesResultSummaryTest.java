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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class KarutaQuizzesResultSummaryTest {

    private KarutaQuizzesResultSummary karutaQuizzesResultSummary;

    @Before
    public void setUp() throws Exception {
        karutaQuizzesResultSummary = new KarutaQuizzesResultSummary(100, 60, 3f);
    }

    @Test
    public void quizCount() throws Exception {
        assertThat(karutaQuizzesResultSummary.quizCount(), is(100));
    }

    @Test
    public void correctCount() throws Exception {
        assertThat(karutaQuizzesResultSummary.correctCount(), is(60));
    }

    @Test
    public void averageAnswerTime() throws Exception {
        assertThat(karutaQuizzesResultSummary.averageAnswerTime(), is(3f));
    }

    @Test
    public void score() throws Exception {
        assertThat(karutaQuizzesResultSummary.score(), is("60/100"));
    }
}