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

import java.util.ArrayList;
import java.util.List;

import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIdentifier;
import me.rei_m.hyakuninisshu.domain.model.karuta.KarutaIds;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class KarutaQuizzesTest {

    public static class WhenNotEmpty {

        private KarutaQuizzes karutaQuizzes;

        private List<KarutaQuiz> value;

        @Before
        public void setUp() throws Exception {
            value = new ArrayList<KarutaQuiz>() {
                {
                    add(createMockKarutaQuiz(1, true));
                    add(createMockKarutaQuiz(2, false));
                    add(createMockKarutaQuiz(3, true));
                    add(createMockKarutaQuiz(4, false));
                    add(createMockKarutaQuiz(5, true));
                }
            };
            karutaQuizzes = new KarutaQuizzes(value);
        }

        @Test
        public void asList() throws Exception {
            assertThat(karutaQuizzes.asList(), is(value));
        }

        @Test
        public void isEmpty() throws Exception {
            assertThat(karutaQuizzes.isEmpty(), is(false));
        }

        @Test
        public void wrongKarutaIds() throws Exception {
            KarutaIds expected = new KarutaIds(new ArrayList<KarutaIdentifier>() {
                {
                    add(new KarutaIdentifier(2));
                    add(new KarutaIdentifier(4));
                }
            });
            assertThat(karutaQuizzes.wrongKarutaIds(), is(expected));
        }

        @Test
        public void resultSummary() throws Exception {
            KarutaQuizzesResultSummary expected = new KarutaQuizzesResultSummary(
                    5,
                    3,
                    5.0F
            );
            assertThat(karutaQuizzes.resultSummary(), is(expected));
        }
    }

    public static class WhenEmpty {

        private KarutaQuizzes karutaQuizzes;

        private List<KarutaQuiz> value;

        @Before
        public void setUp() throws Exception {
            value = new ArrayList<>();
            karutaQuizzes = new KarutaQuizzes(value);
        }

        @Test
        public void asList() throws Exception {
            assertThat(karutaQuizzes.asList(), is(value));
        }

        @Test
        public void isEmpty() throws Exception {
            assertThat(karutaQuizzes.isEmpty(), is(true));
        }

        @Test
        public void wrongKarutaIds() throws Exception {
            KarutaIds expected = new KarutaIds(new ArrayList<>());
            assertThat(karutaQuizzes.wrongKarutaIds(), is(expected));
        }

        @Test
        public void resultSummary() throws Exception {
            KarutaQuizzesResultSummary expected = new KarutaQuizzesResultSummary(
                    0,
                    0,
                    0.0F
            );
            assertThat(karutaQuizzes.resultSummary(), is(expected));
        }
    }

    public static class WhenNotFinished {
        private KarutaQuizzes karutaQuizzes;

        private List<KarutaQuiz> value;

        @Before
        public void setUp() throws Exception {
            value = new ArrayList<KarutaQuiz>() {
                {
                    add(createMockKarutaQuiz(1, true));
                    add(mock(KarutaQuiz.class));
                }
            };
            karutaQuizzes = new KarutaQuizzes(value);
        }

        @Test(expected = IllegalStateException.class)
        public void resultSummary() throws Exception {
            karutaQuizzes.resultSummary();
        }
    }

    private static KarutaQuiz createMockKarutaQuiz(int karutaId, boolean isCorrect) {
        KarutaQuiz karutaQuiz = mock(KarutaQuiz.class);
        KarutaQuizResult result = new KarutaQuizResult(
                new KarutaIdentifier(karutaId),
                ChoiceNo.FIRST,
                isCorrect,
                5000L
        );
        when(karutaQuiz.result()).thenReturn(result);
        return karutaQuiz;
    }
}
